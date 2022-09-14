package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.domain.timeTable.TimeTableRepository;
import com.jaramgroupware.mms.dto.event.serviceDto.EventResponseServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventUpdateRequestServiceDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberResponseServiceDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableAddRequestServiceDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableResponseServiceDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TimeTableService {

    @Autowired
    private final TimeTableRepository timeTableRepository;

    @Transactional(readOnly = true)
    public TimeTableResponseServiceDto findById(Long id){

        TimeTable targetTimeTable = timeTableRepository.findTimeTableById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_TIMETABLE_ID));

        return new TimeTableResponseServiceDto(targetTimeTable);
    }

    @Transactional(readOnly = true)
    public List<TimeTableResponseServiceDto> findAll(){

        return timeTableRepository.findAllBy()
                .orElseThrow(() -> new CustomException(ErrorCode.EMPTY_TIMETABLE))
                .stream()
                .map(TimeTableResponseServiceDto::new)
                .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public List<TimeTableResponseServiceDto> findAll(Specification<TimeTable> specification, Pageable pageable){

        return timeTableRepository.findAll(specification,pageable)
                .stream()
                .map(TimeTableResponseServiceDto::new)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<TimeTableResponseServiceDto> findAll(Specification<TimeTable> specification){

        return timeTableRepository.findAll(specification)
                .stream()
                .map(TimeTableResponseServiceDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long add(TimeTableAddRequestServiceDto timeTableAddRequestServiceDto,String who){
        TimeTable targetTimeTable = timeTableAddRequestServiceDto.toEntity();
        targetTimeTable.setCreateBy(who);
        targetTimeTable.setModifiedBy(who);
        return timeTableRepository.save(targetTimeTable).getId();
    }

    @Transactional
    public Long delete(Long id){
        TimeTable targetTimeTable =  timeTableRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_TIMETABLE_ID));

        timeTableRepository.delete(targetTimeTable);

        return id;
    }

    @Transactional
    public TimeTableResponseServiceDto update(Long id, TimeTableUpdateRequestServiceDto timeTableUpdateRequestServiceDto,String who){

        TimeTable targetTimeTable = timeTableRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_TIMETABLE_ID));

        targetTimeTable.update(timeTableUpdateRequestServiceDto.toEntity(),who);

        timeTableRepository.save(targetTimeTable);

        return new TimeTableResponseServiceDto(targetTimeTable);
    }
}
