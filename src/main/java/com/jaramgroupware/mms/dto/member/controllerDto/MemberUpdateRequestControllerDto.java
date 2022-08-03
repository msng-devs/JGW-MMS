package com.jaramgroupware.mms.dto.member.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.major.Major;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.role.Role;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberAddRequestServiceDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberUpdateRequestServiceDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.validation.DateTimeCheck;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DateTimeCheck(startDateTime = "startDateTime",endDateTime = "endDateTime")
public class MemberUpdateRequestControllerDto {


    @NotEmpty(message = "이름이 비여있습니다!")
    private String name;

    @NotEmpty(message = "휴대폰 번호가 비여있습니다!")
    @Pattern(regexp="(^$|[0-9]{11})")
    private String phoneNumber;

    @NotEmpty(message = "전공 정보가 비여있습니다!")
    private Major major;

    @NotEmpty(message = "회원 등급 정보가 없습니다!")
    private Rank rank;

    @NotEmpty(message = "학생번호가 비여있습니다!")
    @Size(max = 10,min=10,message = "학생번호는 10자리여이야 합니다!")
    private String studentID;

    @NotEmpty(message = "Role 등급이 비여있습니다!")
    private Role role;

    @NotEmpty(message = "Email이 비여있습니다!")
    @Email(message = "email 형식이 잘못되었습니다!")
    private String email;

    @Positive(message = "기수는 양수여야 합니다!")
    private Integer year;


    public MemberUpdateRequestServiceDto toServiceDto(){
        return MemberUpdateRequestServiceDto.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .studentID(studentID)
                .major(major)
                .rank(rank)
                .year(year)
                .build();
    }
}
