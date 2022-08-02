package com.jaramgroupware.mms.dto.attendanceCode.controllerDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Builder
public class AttendanceCodeResponseControllerDto {

    private String code;
    private Long timeTableId;
    private Integer minute;

}
