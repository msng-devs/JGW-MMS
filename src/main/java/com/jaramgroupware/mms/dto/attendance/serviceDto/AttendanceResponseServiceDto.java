package com.jaramgroupware.mms.dto.attendance.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.attendance.Attendance;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceResponseControllerDto;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
                .attendanceType(attendanceType)
                .member(member)
                .defDateTime(defDateTime)
                .timeTable(timeTable)
                .index(index)
                .build();
    }

    public AttendanceResponseServiceDto(Attendance attendance){
        id = attendance.getId();
        attendanceType = attendance.getAttendanceType();
        member = attendance.getMember();
        defDateTime = attendance.getDefDateTime();
        timeTable = attendance.getTimeTable();
        index = attendance.getIndex();
    }
}
