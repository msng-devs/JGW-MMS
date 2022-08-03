package com.jaramgroupware.mms.dto.event.controllerDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseControllerDto {

    private Long id;
    private String name;
    private String index;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
