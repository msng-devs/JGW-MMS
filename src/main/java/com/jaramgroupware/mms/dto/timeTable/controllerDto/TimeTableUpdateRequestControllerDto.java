package com.jaramgroupware.mms.dto.timeTable.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.BaseEntity;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.validation.DateTimeCheck;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DateTimeCheck(startDateTime = "startDateTime",endDateTime = "endDateTime")
public class TimeTableUpdateRequestControllerDto {


    @NotEmpty(message = "이름이 비워져있습니다!")
    @Size(max = 50,min=1,message = "이름은 1자 이상 50자 이하이여야 합니다.")
    private String name;

    @NotNull(message = "startTime 은 반드시 입력해야 합니다!")
//    @FutureOrPresent(message = "startTime에 과거 시간은 사용할 수 없습니다!")
    private LocalDateTime startDateTime;


    @NotNull(message = "endTime은 반드시 입력해야 합니다!")
//    @FutureOrPresent(message = "endTime에 과거 시간은 사용할 수 없습니다!")
    private LocalDateTime endDateTime;


    public TimeTableUpdateRequestServiceDto toServiceDto(){
        return TimeTableUpdateRequestServiceDto.builder()
                .name(name)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
