package com.jaramgroupware.mms.dto.event.serviceDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.BaseEntity;
import com.jaramgroupware.mms.domain.event.Event;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EventUpdateRequestServiceDto {

    private String name;
    private String index;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event toEntity(){
        return Event.builder()
                .name(name)
                .index(index)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
