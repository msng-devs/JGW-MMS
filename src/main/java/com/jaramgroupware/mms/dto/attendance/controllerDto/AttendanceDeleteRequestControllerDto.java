package com.jaramgroupware.mms.dto.attendance.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.attendance.AttendanceID;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceAddServiceDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttendanceDeleteRequestControllerDto {

    @Positive(message = "time_table_id의 형식이 잘못되었습니다!")
    @NotNull(message = "time_table_id가 비워져있습니다!")
    private Long timeTableID;

    @Size(min=28,max = 28 ,message= "member_id의 형식이 잘못되었습니다.")
    @NotNull(message = "member_id가 비워져있습니다!")
    private String memberId;

    public AttendanceID toId(){
        return AttendanceID.builder().timeTable(
                TimeTable.builder()
                        .id(timeTableID)
                        .build())
                .member(Member.builder()
                        .id(memberId)
                        .build())
                .build();
    }
}
