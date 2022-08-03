package com.jaramgroupware.mms.dto.attendance.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import java.time.LocalDateTime;
@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceResponseControllerDto {

    private Long id;
    private AttendanceType attendanceType;
    private Member member;
    private DefDateTime defDateTime;
    private TimeTable timeTable;
    private String index;
}
