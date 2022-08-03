package com.jaramgroupware.mms.dto.attendanceType.serviceDto;

import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceTypeResponseServiceDto {

    private Integer id;
    private String name;

    public AttendanceTypeResponseServiceDto(AttendanceType attendanceType){
        id = attendanceType.getId();
        name = attendanceType.getName();
    }

    public AttendanceType toEntity(){
        return AttendanceType.builder()
                .id(id)
                .name(name)
                .build();
    }

}
