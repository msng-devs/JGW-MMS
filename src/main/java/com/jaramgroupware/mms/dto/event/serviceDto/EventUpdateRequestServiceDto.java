package com.jaramgroupware.mms.dto.event.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventUpdateRequestServiceDto {

    private String name;
    private String index;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event toEntity(){
        return Event.builder()
                .name(name)
                .index(index)
                .defDateTime(DefDateTime.builder()
                        .createdDateTime(startDateTime)
                        .modifiedDataTime(endDateTime)
                        .build())
                .build();
    }
}
