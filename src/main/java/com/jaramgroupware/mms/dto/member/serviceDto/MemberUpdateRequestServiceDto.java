package com.jaramgroupware.mms.dto.member.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.major.Major;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.role.Role;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateRequestServiceDto {

    private String name;
    private String phoneNumber;
    private Major major;
    private Rank rank;
    private Role role;
    private Integer year;
    private String email;
    private String studentID;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .studentID(studentID)
                .major(major)
                .rank(rank)
                .role(role)
                .year(year)
                .build();
    }
}
