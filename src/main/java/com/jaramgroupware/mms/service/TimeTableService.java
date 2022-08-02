package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.domain.timeTable.TimeTableRepository;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableResponseServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
