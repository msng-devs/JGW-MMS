package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.dto.attendanceCode.controllerDto.AttendanceCodeAddRequestControllerDto;
import com.jaramgroupware.mms.dto.attendanceCode.controllerDto.AttendanceCodeResponseControllerDto;
import com.jaramgroupware.mms.dto.attendanceCode.serviceDto.AttendanceCodeServiceDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventAddRequestControllerDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventResponseControllerDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableAddRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableResponseControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableAddRequestServiceDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableResponseServiceDto;
import com.jaramgroupware.mms.service.AttendanceCodeService;
import com.jaramgroupware.mms.service.EventService;
import com.jaramgroupware.mms.service.TimeTableService;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import com.jaramgroupware.mms.utils.key.KeyGenerator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/v1/timetable")
public class TimeTableApiController {

    private final TimeTableService timeTableService;
    private final EventService eventService;
    private final AttendanceCodeService attendanceCodeService;
    private final KeyGenerator keyGenerator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping
    public ResponseEntity<Long> addTimeTable(
            @RequestBody @Valid TimeTableAddRequestControllerDto timeTableAddRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Request Add new timetable, TimeTable = ({})",uid,timeTableAddRequestControllerDto.toString());

        Long id = timeTableService.add(
                timeTableAddRequestControllerDto.toServiceDto(
                        eventService.findById(timeTableAddRequestControllerDto.getEventId()).toEntity()
                )
        );

        logger.info("UID = ({}) Successfully Add new TimeTable, EventId = ({})",uid,id);

        return new ResponseEntity<Long>(id,HttpStatus.OK);
    }

    @GetMapping("{timeTableId}")
    public ResponseEntity<TimeTableResponseControllerDto> findTimeTableById(
            @PathVariable Long timeTableId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find TimeTable's info, TimeTableId = ({})",uid,timeTableId);

        TimeTableResponseControllerDto result = timeTableService.findById(timeTableId).toControllerDto();

        logger.info("UID = ({}) Successfully find timeTable, TimeTable = ({})",uid,result.toString());

        return new ResponseEntity<TimeTableResponseControllerDto>(result,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeTableResponseControllerDto>> findTimeTableAll(
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find All TimeTable's info",uid);

        List<TimeTableResponseControllerDto> results = timeTableService.findAll().stream()
                .map(TimeTableResponseServiceDto::toControllerDto)
                .collect(Collectors.toList());

        logger.info("UID = ({}) Successfully find All timeTables",uid);

        return new ResponseEntity<List<TimeTableResponseControllerDto>>(results,HttpStatus.OK);
    }

    @DeleteMapping("{timeTableId}")
    public ResponseEntity<Long> delTimeTable(
            @PathVariable Long timeTableId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try delete timeTable, TimeTableId = ({})",uid,timeTableId);

        timeTableService.delete(timeTableId);

        logger.info("UID = ({}) Successfully delete timeTable, TimeTableId = ({})",uid,timeTableId);

        return new ResponseEntity<Long>(timeTableId, HttpStatus.OK);
    }

    @PutMapping("{eventId}")
    public ResponseEntity<TimeTableResponseControllerDto> updateTimeTable(
            @PathVariable Long eventId,
            @RequestBody @Valid TimeTableUpdateRequestControllerDto timeTableUpdateRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try update Event,  TimeTableID = ({})",uid,eventId);

        TimeTableResponseControllerDto result = timeTableService.update(eventId,timeTableUpdateRequestControllerDto.toServiceDto()).toControllerDto();

        logger.info("UID = ({}) Successfully update event,  TimeTable = ({})",uid,result.toString());

        return new ResponseEntity<TimeTableResponseControllerDto>(result, HttpStatus.OK);
    }
}
