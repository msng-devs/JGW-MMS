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

        //TODO 로직변경
        //해당 ID의 Time table이 있는지 검증
        timeTableService.findById(addRequestControllerDto.getTimeTableId());

        //해당 TimeTable이 code를 가지고 있는지 검증
        if(attendanceCodeService.checkHasKey(addRequestControllerDto.getTimeTableId()))
            throw new CustomException(ErrorCode.ALREADY_HAS_CODE);

        //코드 생성
        String code = keyGenerator.getKey(6);
        attendanceCodeService.createCode(AttendanceCodeServiceDto.builder()
                .minute(addRequestControllerDto.getMinute())
                .code(code)
                .timeTableId(addRequestControllerDto.getTimeTableId())
                .build());

        logger.info("UID = ({}) Successfully create Attendance Code, target TimeTable ID = ({}) code = ({})",uid,addRequestControllerDto.getTimeTableId(),code);

        return new ResponseEntity<String>(code, HttpStatus.OK);
    }

    @DeleteMapping("/{timeTableId}")
    public ResponseEntity<Long> revokeAttendanceCode(
            @PathVariable Long timeTableId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}), timeTableId = ({}) Start revoke code",uid,timeTableId);

        //해당 키가 존재하는지 확인
        if(attendanceCodeService.validationKey(timeTableId)) throw new CustomException(ErrorCode.INVALID_TIMETABLE_ID);

        attendanceCodeService.revokeCode(timeTableId);

        logger.info("UID = ({}), timeTableId = ({}) Successfully revoke Attendance Code",uid,timeTableId);

        return new ResponseEntity<Long>(timeTableId, HttpStatus.OK);
    }

    @GetMapping("/{timeTableId}")
    public ResponseEntity<AttendanceCodeResponseControllerDto> findAttendanceCode(
            @PathVariable Long timeTableId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}), code = ({}) Find code's information",uid,timeTableId);

        AttendanceCodeResponseControllerDto result = attendanceCodeService.findKey(timeTableId).toControllerDto();

        logger.info("UID = ({}), timeTableId = ({}) Get information, AttendanceCode = ({})",uid,timeTableId,result.toString());

        return new ResponseEntity<AttendanceCodeResponseControllerDto>(result,HttpStatus.OK);
    }
}
