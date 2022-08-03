package com.jaramgroupware.mms.web;


import com.jaramgroupware.mms.dto.event.controllerDto.EventAddRequestControllerDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventResponseControllerDto;
import com.jaramgroupware.mms.dto.event.controllerDto.EventUpdateRequestControllerDto;
import com.jaramgroupware.mms.service.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/event")
public class EventApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Long> addEvent(
            @RequestBody @Valid EventAddRequestControllerDto eventAddRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Request Add new Event, Event = ({})",uid,eventAddRequestControllerDto.toString());

        Long id = eventService.add(eventAddRequestControllerDto.toServiceDto());

        logger.info("UID = ({}) Successfully Add new Event, EventId = ({})",uid,id);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @GetMapping("{eventId}")
    public ResponseEntity<EventResponseControllerDto> getEvent(
            @PathVariable Long eventId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find Event's info, EventId = ({})",uid,eventId);

        EventResponseControllerDto result = eventService.findById(eventId).toControllerDto();

        logger.info("UID = ({}) Successfully find event, Event = ({})",uid,result.toString());

        return new ResponseEntity<EventResponseControllerDto>(result, HttpStatus.OK);
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<Long> delEvent(
            @PathVariable Long eventId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try delete Event, EventId = ({})",uid,eventId);

        eventService.delete(eventId);

        logger.info("UID = ({}) Successfully delete event, Event = ({})",uid,eventId);

        return new ResponseEntity<Long>(eventId, HttpStatus.OK);
    }

    @PutMapping("{eventId}")
    public ResponseEntity<EventResponseControllerDto> updateEvent(
            @PathVariable Long eventId,
            @RequestBody @Valid EventUpdateRequestControllerDto eventUpdateRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try update Event, EventId = ({})",uid,eventId);

        EventResponseControllerDto result = eventService.update(eventId,eventUpdateRequestControllerDto.toServiceDto()).toControllerDto();

        logger.info("UID = ({}) Successfully update event, Event = ({})",uid,result.toString());

        return new ResponseEntity<EventResponseControllerDto>(result, HttpStatus.OK);
    }
}
