package com.jaramgroupware.mms.dto.attendance.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.attendance.Attendance;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceUpdateRequestServiceDto {
    @NotNull(message = "출결 유형이 비워져 있습니다!")
    private AttendanceType attendanceType;

    @Size(max = 255 ,message= "입력 가능한 전체 글자수는 255자입니다. ")
    private String index;

    public Attendance toEntity(){
        return Attendance.builder()
                .attendanceType(attendanceType)
                .index(index)
                .build();
    }
}
