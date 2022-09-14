package com.jaramgroupware.mms.dto.attendance.serviceDto;

import com.jaramgroupware.mms.domain.attendance.Attendance;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceBulkUpdateRequestServiceDto {

    private TimeTable timeTable;
    private Member member;
    private AttendanceType attendanceType;
    private String index;

    public Attendance toEntity(){
        return Attendance.builder()
                .timeTable(timeTable)
                .member(member)
                .attendanceType(attendanceType)
                .index(index)
                .build();
    }

    @Override
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }
}
