//package com.jaramgroupware.mms.web;
//
//import com.jaramgroupware.mms.dto.event.controllerDto.EventAddRequestControllerDto;
//import com.jaramgroupware.mms.dto.event.controllerDto.EventResponseControllerDto;
//import com.jaramgroupware.mms.dto.event.controllerDto.EventUpdateRequestControllerDto;
//import com.jaramgroupware.mms.dto.event.serviceDto.EventResponseServiceDto;
//import com.jaramgroupware.mms.service.AttendanceCodeService;
//import com.jaramgroupware.mms.service.AttendanceService;
//import com.jaramgroupware.mms.utils.exception.CustomException;
//import com.jaramgroupware.mms.utils.exception.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/attendance")
//public class AttendanceApiController {
//
//    private final AttendanceService attendanceService;
//    private final AttendanceCodeService attendanceCodeService;
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @PostMapping
//    public ResponseEntity<Long> addAttendance(
//            @RequestBody @Valid EventAddRequestControllerDto eventAddRequestControllerDto,
//            @RequestHeader("user_uid") String uid){
//
//        logger.info("UID = ({}) Request Add new Event, add = ({})",uid,eventAddRequestControllerDto.toString());
//
//        Long id = eventService.add(eventAddRequestControllerDto.toServiceDto());
//
//        logger.info("UID = ({}) Successfully Add new Event, EventId = ({})",uid,id);
//
//        return new ResponseEntity<Long>(id, HttpStatus.OK);
//    }
//
//    @GetMapping("{eventId}")
//    public ResponseEntity<EventResponseControllerDto> getEventById(
//            @PathVariable Long eventId,
//            @RequestHeader("user_uid") String uid){
//
//        logger.info("UID = ({}) Try find Event's info, EventId = ({})",uid,eventId);
//
//        EventResponseControllerDto result = eventService.findById(eventId).toControllerDto();
//
//        logger.info("UID = ({}) Successfully find event, Event = ({})",uid,result.toString());
//
//        return new ResponseEntity<EventResponseControllerDto>(result, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<EventResponseControllerDto>> getEventAll(
//            @RequestHeader("user_uid") String uid){
//
//        logger.info("UID = ({}) Try find All Event's info",uid);
//
//        List<EventResponseControllerDto> results = eventService.findAll()
//                .stream().map(EventResponseServiceDto::toControllerDto)
//                .collect(Collectors.toList());
//
//        if(results.size() == 0) throw new CustomException(ErrorCode.EMPTY_EVENT);
//
//        logger.info("UID = ({}) Successfully find All events",uid);
//
//        return new ResponseEntity<List<EventResponseControllerDto>>(results, HttpStatus.OK);
//    }
//
//
//    @DeleteMapping("{eventId}")
//    public ResponseEntity<Long> delEvent(
//            @PathVariable Long eventId,
//            @RequestHeader("user_uid") String uid){
//
//        logger.info("UID = ({}) Try delete Event, EventId = ({})",uid,eventId);
//
//        eventService.delete(eventId);
//
//        logger.info("UID = ({}) Successfully delete event, Event = ({})",uid,eventId);
//
//        return new ResponseEntity<Long>(eventId, HttpStatus.OK);
//    }
//
//    @PutMapping("{eventId}")
//    public ResponseEntity<EventResponseControllerDto> updateEvent(
//            @PathVariable Long eventId,
//            @RequestBody @Valid EventUpdateRequestControllerDto eventUpdateRequestControllerDto,
//            @RequestHeader("user_uid") String uid){
//
//        logger.info("UID = ({}) Try update Event, EventId = ({})",uid,eventId);
//
//        EventResponseControllerDto result = eventService.update(eventId,eventUpdateRequestControllerDto.toServiceDto()).toControllerDto();
//
//        logger.info("UID = ({}) Successfully update event, Event = ({})",uid,result.toString());
//
//        return new ResponseEntity<EventResponseControllerDto>(result, HttpStatus.OK);
//    }
//}
