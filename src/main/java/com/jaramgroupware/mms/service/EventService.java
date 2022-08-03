package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.event.EventRepository;
import com.jaramgroupware.mms.dto.event.controllerDto.EventResponseControllerDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventAddRequestServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventResponseServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Transactional
    public Long add(EventAddRequestServiceDto eventAddRequestServiceDto){
        return eventRepository.save(eventAddRequestServiceDto.toEntity()).getId();
    }

    @Transactional
    public Long delete(Long id){

        Event targetEvent =  eventRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_EVENT_ID));

        eventRepository.delete(targetEvent);

        return id;
    }

    @Transactional(readOnly = true)
    public EventResponseServiceDto findById(Long id){

        Event targetEvent = eventRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_EVENT_ID));

        return EventResponseServiceDto.builder()
                .id(targetEvent.getId())
                .name(targetEvent.getName())
                .index(targetEvent.getIndex())
                .defDateTime(targetEvent.getDefDateTime())
                .build();
    }

    @Transactional
    public EventResponseServiceDto update(Long id, EventUpdateRequestServiceDto updateRequestServiceDto){

        Event targetEvent = eventRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_EVENT_ID));

        targetEvent.update(updateRequestServiceDto.toEntity());

        eventRepository.save(targetEvent);

        return EventResponseServiceDto.builder()
                .id(targetEvent.getId())
                .id(targetEvent.getId())
                .name(targetEvent.getName())
                .index(targetEvent.getIndex())
                .defDateTime(targetEvent.getDefDateTime())
                .build();
    }
}
