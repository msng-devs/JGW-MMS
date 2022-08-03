package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.attendance.Attendance;
import com.jaramgroupware.mms.domain.attendance.AttendanceRepository;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceAddServiceDto;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceUpdateRequestServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventResponseServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Long add(AttendanceAddServiceDto attendanceAddServiceDto){
        return attendanceRepository.save(attendanceAddServiceDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public AttendanceResponseServiceDto findById(Long id){
        Attendance targetAttendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ATTENDANCE_ID));
        return new AttendanceResponseServiceDto(targetAttendance);
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseServiceDto> findAll(){
        return attendanceRepository.findAllBy()
                .orElseThrow(()->new CustomException(ErrorCode.EMPTY_ATTENDANCE))
                .stream()
                .map(AttendanceResponseServiceDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long delete(Long id){
        Attendance targetAttendance = attendanceRepository.findAttendanceById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ATTENDANCE_ID));

        attendanceRepository.delete(targetAttendance);

        return id;
    }

    @Transactional
    public AttendanceResponseServiceDto update(Long id, AttendanceUpdateRequestServiceDto attendanceUpdateRequestServiceDto){

        Attendance targetAttendance = attendanceRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_ATTENDANCE_ID));

        targetAttendance.update(attendanceUpdateRequestServiceDto.toEntity());

        attendanceRepository.save(targetAttendance);

        return new AttendanceResponseServiceDto(targetAttendance);
    }

}
