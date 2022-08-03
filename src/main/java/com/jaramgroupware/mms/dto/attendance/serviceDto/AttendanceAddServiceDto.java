package com.jaramgroupware.mms.dto.attendance.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
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

    public Attendance toEntity(){
        return Attendance.builder()
                .attendanceType(attendanceType)
                .member(member)
                .defDateTime(DefDateTime
                        .builder()
                        .createdDateTime(LocalDateTime.now())
                        .modifiedDataTime(LocalDateTime.now())
                        .build())
                .timeTable(timeTable)
                .index(index)
                .build();
    }
}
