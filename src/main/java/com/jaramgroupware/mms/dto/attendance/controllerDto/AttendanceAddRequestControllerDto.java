package com.jaramgroupware.mms.dto.attendance.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceAddServiceDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceAddRequestControllerDto {

    @NotNull(message = "Target TimeTable 이 비워져있습니다!")
    private TimeTable timeTable;

    @NotNull(message = "멤버가 비워져있습니다!")
    private Member member;

    @NotNull(message = "출결 유형이 비워져 있습니다!")
    private AttendanceType attendanceType;

    @Size(max = 255 ,message= "입력 가능한 전체 글자수는 255자입니다. ")
    private String index;

    public AttendanceAddServiceDto toServiceDto(){
        return AttendanceAddServiceDto.builder()
                .index(index)
                .timeTable(timeTable)
                .member(member)
                .attendanceType(attendanceType)
                .build();
    }

}
