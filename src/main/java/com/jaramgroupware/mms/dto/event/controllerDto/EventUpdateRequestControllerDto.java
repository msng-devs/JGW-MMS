package com.jaramgroupware.mms.dto.event.controllerDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.dto.event.serviceDto.EventAddRequestServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.validation.DateTimeValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class EventUpdateRequestControllerDto {


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


    public EventUpdateRequestServiceDto toServiceDto(){
        return EventUpdateRequestServiceDto.builder()
                .name(name)
                .index(index)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
