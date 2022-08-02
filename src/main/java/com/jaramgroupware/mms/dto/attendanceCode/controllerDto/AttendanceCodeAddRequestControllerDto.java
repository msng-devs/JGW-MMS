package com.jaramgroupware.mms.dto.attendanceCode.controllerDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@NoArgsConstructor
@Builder
public class AttendanceCodeAddRequestControllerDto {

    @NotEmpty
    private Long timeTableId;

    @Min(1)
    private Integer minute;

}
