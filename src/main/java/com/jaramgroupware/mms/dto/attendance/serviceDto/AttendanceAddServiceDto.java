package com.jaramgroupware.mms.dto.attendance.serviceDto;

import com.jaramgroupware.mms.domain.BaseEntity;
import com.jaramgroupware.mms.domain.attendance.Attendance;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceAddServiceDto {

    private AttendanceType attendanceType;
    private Member member;
    private TimeTable timeTable;
    private String index;
    private String who;

    public Attendance toEntity(){
        return Attendance.builder()
                .attendanceType(attendanceType)
                .member(member)
                .timeTable(timeTable)
                .index(index)
                .build();
    }

    @Override
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }

}
