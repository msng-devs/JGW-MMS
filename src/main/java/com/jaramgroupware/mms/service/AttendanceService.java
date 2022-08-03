package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.attendance.AttendanceRepository;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceAddServiceDto;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Long register(AttendanceAddServiceDto attendanceAddServiceDto){
        return attendanceRepository.save(attendanceAddServiceDto.toEntity()).getId();
    }
}
