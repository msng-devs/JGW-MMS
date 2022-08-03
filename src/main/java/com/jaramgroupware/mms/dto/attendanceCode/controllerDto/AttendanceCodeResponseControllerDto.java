package com.jaramgroupware.mms.dto.attendanceCode.controllerDto;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class AttendanceCodeResponseControllerDto {

    private String code;
    private Long timeTableId;
    private Integer minute;

}
