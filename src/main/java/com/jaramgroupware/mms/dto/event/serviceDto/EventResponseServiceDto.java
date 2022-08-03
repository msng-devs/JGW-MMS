package com.jaramgroupware.mms.dto.event.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.dto.event.controllerDto.EventResponseControllerDto;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseServiceDto {

    private Long id;
    private String name;
    private String index;
    private DefDateTime defDateTime;

    public EventResponseControllerDto toControllerDto(){

        return EventResponseControllerDto.builder()
                .id(id)
                .name(name)
                .index(index)
                .startDateTime(defDateTime.getCreatedDateTime())
                .endDateTime(defDateTime.getModifiedDataTime())
                .build();
    }

    public EventResponseServiceDto(Event event){
        id = event.getId();
        name = event.getName();
        index = event.getIndex();
        defDateTime = event.getDefDateTime();
    }

    public Event toEntity(){
        return Event.builder()
                .id(id)
                .name(name)
                .index(index)
                .defDateTime(defDateTime)
                .build();
    }

}
