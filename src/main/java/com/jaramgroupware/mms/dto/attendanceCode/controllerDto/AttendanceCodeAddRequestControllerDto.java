package com.jaramgroupware.mms.dto.attendanceCode.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceCodeAddRequestControllerDto {

    @NotNull(message = "TimeTableID가 비워져있습니다!")
    @Positive(message = "TimeTableID의 값이 적절하지 않습니다!")
    private Long timeTableId;

    @NotNull
    @Range(min = 1,max = 1440,message = "최소 시간은 1분, 최대 시간은 1440분까지만 가능합니다.")
    private Integer minute;

}
