package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.dto.attendanceCode.controllerDto.AttendanceCodeAddRequestControllerDto;
import com.jaramgroupware.mms.dto.attendanceCode.controllerDto.AttendanceCodeResponseControllerDto;
import com.jaramgroupware.mms.dto.attendanceCode.serviceDto.AttendanceCodeServiceDto;
import com.jaramgroupware.mms.service.AttendanceCodeService;
import com.jaramgroupware.mms.service.TimeTableService;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import com.jaramgroupware.mms.utils.key.KeyGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/attendance-code")
public class AttendanceCodeApiController {

    @Autowired
    private final TimeTableService timeTableService;

    @Autowired
    private final AttendanceCodeService attendanceCodeService;

    private final KeyGenerator keyGenerator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<String> createdAttendanceCode(
            @RequestBody @Valid AttendanceCodeAddRequestControllerDto addRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Start create Attendance Code for TimeTable ID = ({})",uid,addRequestControllerDto.getTimeTableId());

        //해당 ID의 Time table이 있는지 검증
        timeTableService.findById(addRequestControllerDto.getTimeTableId());

        //해당 TimeTable이 code를 가지고 있는지 검증
        if(attendanceCodeService.checkHasKey(addRequestControllerDto.getTimeTableId()))
            throw new CustomException(ErrorCode.ALREADY_HAS_CODE);

        //랜덤 문자열 생성 6자리
        String key = "";

        //키 생성 한도는 100회로 제한(무한 루프 빠지는거 방지)
        for (int i = 0; i < 100; i++) {
            String idxKey  = keyGenerator.getKey(6);
            if(attendanceCodeService.validationKey(key)){
                key = idxKey;
                break;
            }
        }

        //키 생성이 안됬다면, 무한 루프 방지를 위하여 중단
        if(key.equals("")) throw new CustomException(ErrorCode.CANNOT_CREATE_KEY);

        //정상적으로 키가 생성됬다면, redis 에 등록
        attendanceCodeService.createCode(AttendanceCodeServiceDto
                .builder()
                .code(key)
                .timeTableId(addRequestControllerDto.getTimeTableId())
                .minute(addRequestControllerDto.getMinute())
                .build());

        logger.info("UID = ({}) Successfully create Attendance Code, target TimeTable ID = ({}) code = ({})",uid,addRequestControllerDto.getTimeTableId(),key);

        return new ResponseEntity<String>(key, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> revokeAttendanceCode(
            @PathVariable String code,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}), Code = ({}) Start revoke code",uid,code);

        //해당 키가 존재하는지 확인
        if(attendanceCodeService.validationKey(code)) throw new CustomException(ErrorCode.INVALID_ATTENDANCE_CODE);

        attendanceCodeService.revokeCode(code);

        logger.info("UID = ({}), code = ({}) Successfully revoke Attendance Code",uid,code);

        return new ResponseEntity<String>(code, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<AttendanceCodeResponseControllerDto> findAttendanceCode(
            @PathVariable String code,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}), code = ({}) Find code's information",uid,code);

        AttendanceCodeServiceDto result = attendanceCodeService.findKey(code);

        logger.info("UID = ({}), code = ({}) Get information, TimeTableId = ({}), expireTime = ({})",uid,code,result.getTimeTableId(),result.getMinute());

        return new ResponseEntity<AttendanceCodeResponseControllerDto>(result.toControllerDto(),HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<AttendanceCodeResponseControllerDto> findAttendanceCode(
            @RequestParam("time-table-id") Long timeTableID,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}), timeTableID = ({}) Find code's information",uid,timeTableID);

        AttendanceCodeServiceDto result = attendanceCodeService.findKeyById(timeTableID);

        logger.info("UID = ({}), timeTableID = ({}) Get information, TimeTableId = ({}), expireTime = ({})",uid,timeTableID,result.getTimeTableId(),result.getMinute());

        return new ResponseEntity<AttendanceCodeResponseControllerDto>(result.toControllerDto(),HttpStatus.OK);

    }
}
