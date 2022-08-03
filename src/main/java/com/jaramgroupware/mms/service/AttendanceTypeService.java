package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceTypeRepository;
import com.jaramgroupware.mms.dto.attendanceType.serviceDto.AttendanceTypeResponseServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendanceTypeService {

    private final AttendanceTypeRepository attendanceTypeRepository;

    @Transactional(readOnly = true)
    public AttendanceTypeResponseServiceDto findById(Integer id){
        AttendanceType targetAttendanceType = attendanceTypeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ATTENDANCE_TYPE_ID));
        return new AttendanceTypeResponseServiceDto(targetAttendanceType);
    }
}
