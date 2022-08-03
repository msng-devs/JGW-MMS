package com.jaramgroupware.mms.dto.event.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.dto.event.serviceDto.EventAddRequestServiceDto;
import com.jaramgroupware.mms.utils.validation.DateTimeCheck;
import com.jaramgroupware.mms.utils.validation.DateTimeValidation;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DateTimeCheck(startDateTime = "startDateTime",endDateTime = "endDateTime")
public class EventAddRequestControllerDto {


    @NotEmpty(message = "Event 이름이 비워져있습니다!")
    @Size(max = 50,min=1,message = "Event 이름은 1자 이상 50자 이하이여야 합니다.")
    private String name;

    private String index;


    @NotNull(message = "startTime 은 반드시 입력해야 합니다!")
//    @FutureOrPresent(message = "startTime에 과거 시간은 사용할 수 없습니다!")
    private LocalDateTime startDateTime;


    @NotNull(message = "endTime은 반드시 입력해야 합니다!")
//    @FutureOrPresent(message = "endTime에 과거 시간은 사용할 수 없습니다!")
    private LocalDateTime endDateTime;


    public EventAddRequestServiceDto toServiceDto(){
        return EventAddRequestServiceDto.builder()
                .name(name)
                .index(index)
                .starDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}