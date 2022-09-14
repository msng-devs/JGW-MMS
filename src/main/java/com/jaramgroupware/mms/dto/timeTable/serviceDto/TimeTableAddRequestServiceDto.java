package com.jaramgroupware.mms.dto.timeTable.serviceDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.BaseEntity;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TimeTableAddRequestServiceDto {

    private String name;
    private Event event;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public TimeTable toEntity(){
        return TimeTable.builder()
                .name(name)
                .event(event)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
    @Override
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }
}
