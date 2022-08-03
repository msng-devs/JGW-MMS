package com.jaramgroupware.mms.dto.timeTable.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTableUpdateRequestServiceDto {

    private String name;
    private DefDateTime defDateTime;

    public TimeTable toEntity(){
        return TimeTable.builder()
                .name(name)
                .defDateTime(defDateTime)
                .build();
    }
}
