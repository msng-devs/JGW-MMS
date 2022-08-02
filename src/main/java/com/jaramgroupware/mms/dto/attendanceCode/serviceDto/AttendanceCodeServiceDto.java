package com.jaramgroupware.mms.dto.attendanceCode.serviceDto;

import com.jaramgroupware.mms.dto.attendanceCode.controllerDto.AttendanceCodeResponseControllerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Builder
public class AttendanceCodeServiceDto {

    private String code;
    private Long timeTableId;
    private Integer minute;

    public AttendanceCodeResponseControllerDto toControllerDto(){
        return AttendanceCodeResponseControllerDto.builder()
                .code(code)
                .timeTableId(timeTableId)
                .minute(minute)
                .build();
    }
}
