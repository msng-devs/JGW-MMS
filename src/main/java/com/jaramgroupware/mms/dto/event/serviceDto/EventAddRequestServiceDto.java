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
public class EventAddRequestServiceDto {

    private String name;
    private String index;
    private LocalDateTime starDateTime;
    private LocalDateTime endDateTime;

    public Event toEntity(){
        return Event.builder()
                .name(name)
                .index(index)
                .defDateTime(DefDateTime.builder()
                        .createdDateTime(starDateTime)
                        .modifiedDataTime(endDateTime)
                        .build())
                .build();
    }
}
