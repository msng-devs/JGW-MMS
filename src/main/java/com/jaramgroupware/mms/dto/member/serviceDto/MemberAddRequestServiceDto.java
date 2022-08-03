package com.jaramgroupware.mms.dto.member.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.major.Major;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.role.Role;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAddRequestServiceDto {

    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String studentID;
    private Major major;
    private Rank rank;
    private Role role;
    private Integer year;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .studentID(studentID)
                .major(major)
                .rank(rank)
                .role(role)
                .year((year != null)? year : LocalDate.now().getYear()-1984)
                .build();
    }
}
