package com.jaramgroupware.mms.dto.attendanceCode.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.attendance.Attendance;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceAddServiceDto;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceCodeRegisterRequestControllerDto {

    @NotNull(message = "Target TimeTable 이 비워져있습니다!")
    private TimeTable timeTable;

    //TODO 하드코딩 고치기
    @NotEmpty(message = "코드가 비워져있습니다!")
    @Size(max = 6,min = 6,message = "코드의 길이가 다릅니다!")
    private String code;

    @NotNull(message = "멤버가 비워져있습니다!")
    private Member member;

    public AttendanceAddServiceDto toAttendanceServiceDto(AttendanceType attendanceType){
        return AttendanceAddServiceDto.builder()
                .member(member)
                .timeTable(timeTable)
                .attendanceType(attendanceType)
                .index("출결 코드를 통해 자동적으로 출결처리가 되었습니다.")
                .build();
    }
}
