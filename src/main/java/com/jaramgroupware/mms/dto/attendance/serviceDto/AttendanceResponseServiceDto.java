package com.jaramgroupware.mms.dto.attendance.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceResponseControllerDto;

import javax.persistence.*;

public class AttendanceResponseServiceDto {

    private Long id;
    private AttendanceType attendanceType;
    private Member member;
    private DefDateTime defDateTime;
    private TimeTable timeTable;
    private String index;

    public AttendanceResponseControllerDto toControllerDto(){
        return AttendanceResponseControllerDto.builder()
                .id(id)
                .attendanceType(attendanceType.getName())
                .memberName(member.getName())
                .createdDateTime(defDateTime.getCreatedDateTime())
                .timeTable(timeTable)
                .index(index)
                .build();
    }
}
