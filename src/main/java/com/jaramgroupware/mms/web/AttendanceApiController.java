package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceAddRequestControllerDto;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceResponseControllerDto;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventAddRequestControllerDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventResponseControllerDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventResponseServiceDto;
import com.jaramgroupware.mms.service.AttendanceCodeService;
import com.jaramgroupware.mms.service.AttendanceService;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;
    private final AttendanceCodeService attendanceCodeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<Long> addAttendance(
            @RequestBody @Valid AttendanceAddRequestControllerDto attendanceAddRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Request Add new Attendance, add = ({})",uid,attendanceAddRequestControllerDto.toString());

        Long id = attendanceService.add(attendanceAddRequestControllerDto.toServiceDto());

        logger.info("UID = ({}) Successfully Add new Attendance, AttendanceId = ({})",uid,id);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @GetMapping("{eventId}")
    public ResponseEntity<AttendanceResponseControllerDto> getAttendanceById(
            @PathVariable Long eventId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find Attendance's info, AttendanceID = ({})",uid,eventId);

        AttendanceResponseControllerDto result = attendanceService.findById(eventId).toControllerDto();

        logger.info("UID = ({}) Successfully find Attendance, Attendance = ({})",uid,result.toString());

        return new ResponseEntity<AttendanceResponseControllerDto>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponseControllerDto>> getAttendanceAll(
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find All Attendance's info",uid);

        List<AttendanceResponseControllerDto> results = attendanceService.findAll()
                .stream().map(AttendanceResponseServiceDto::toControllerDto)
                .collect(Collectors.toList());

        logger.info("UID = ({}) Successfully find All events",uid);

        return new ResponseEntity<List<AttendanceResponseControllerDto>>(results, HttpStatus.OK);
    }


    @DeleteMapping("{attendanceID}")
    public ResponseEntity<Long> delAttendance(
            @PathVariable Long attendanceID,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try delete Attendance, Attendance = ({})",uid,attendanceID);

        attendanceService.delete(attendanceID);

        logger.info("UID = ({}) Successfully delete Attendance, Attendance= ({})",uid,attendanceID);

        return new ResponseEntity<Long>(attendanceID, HttpStatus.OK);
    }

    @PutMapping("{attendanceID}")
    public ResponseEntity<AttendanceResponseControllerDto> updateAttendance(
            @PathVariable Long attendanceID,
            @RequestBody @Valid AttendanceUpdateRequestControllerDto attendanceUpdateRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try update Event, AttendanceId = ({})",uid,attendanceID);

        AttendanceResponseControllerDto result = attendanceService.update(attendanceID,attendanceUpdateRequestControllerDto.toServiceDto()).toControllerDto();

        logger.info("UID = ({}) Successfully update Attendance, Attendance = ({})",uid,result.toString());

        return new ResponseEntity<AttendanceResponseControllerDto>(result, HttpStatus.OK);
    }
}
