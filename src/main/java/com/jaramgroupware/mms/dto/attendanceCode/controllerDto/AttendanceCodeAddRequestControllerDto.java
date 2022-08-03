package com.jaramgroupware.mms.dto.attendanceCode.controllerDto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class AttendanceCodeAddRequestControllerDto {

    @NotEmpty
    private Long timeTableId;

    @Min(1)
    private Integer minute;

}
