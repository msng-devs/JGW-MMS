package com.jaramgroupware.mms.dto.member.serviceDto;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.major.Major;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.role.Role;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.dto.member.controllerDto.MemberResponseControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableResponseControllerDto;
import lombok.*;


@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseServiceDto {

    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String studentID;
    private Major major;
    private Rank rank;
    private Role role;
    private Integer year;
    private DefDateTime defDateTime;

    public MemberResponseServiceDto(Member member){
        id = member.getId();
        email = member.getEmail();
        name = member.getName();
        phoneNumber = member.getPhoneNumber();
        studentID = member.getStudentID();
        major = member.getMajor();
        role = member.getRole();
        year = member.getYear();
        defDateTime = member.getDefDateTime();
    }

    public MemberResponseControllerDto toControllerDto(){
        return MemberResponseControllerDto
                .builder()
                .id(id)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .studentID(studentID)
                .major(major)
                .role(role)
                .year(year)
                .createdDateTime(defDateTime.getCreatedDateTime())
                .modifiedDateTime(defDateTime.getModifiedDataTime())
                .build();
    }

}
