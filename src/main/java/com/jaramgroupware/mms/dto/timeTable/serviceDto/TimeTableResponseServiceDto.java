package com.jaramgroupware.mms.dto.timeTable.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;


@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTableResponseServiceDto {

    private Long id;
    private DefDateTime defDateTime;
    private Event event;

    public TimeTableResponseServiceDto(TimeTable timeTable){
        id = timeTable.getId();
        defDateTime = timeTable.getDefDateTime();
        event = timeTable.getEvent();
    }
}
