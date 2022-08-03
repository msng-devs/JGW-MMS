package com.jaramgroupware.mms.dto.timeTable.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableResponseControllerDto;
import lombok.*;


@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTableResponseServiceDto {

    private Long id;
    private String name;
    private DefDateTime defDateTime;
    private Event event;

    public TimeTableResponseServiceDto(TimeTable timeTable){
        id = timeTable.getId();
        name = timeTable.getName();
        defDateTime = timeTable.getDefDateTime();
        event = timeTable.getEvent();
    }

    public TimeTableResponseControllerDto toControllerDto(){
        return TimeTableResponseControllerDto
                .builder()
                .id(id)
                .name(name)
                .endDateTime(defDateTime.getModifiedDataTime())
                .startDateTime(defDateTime.getCreatedDateTime())
                .event(event)
                .build();
    }

}
