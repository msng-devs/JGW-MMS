package com.jaramgroupware.mms.domain.member;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.major.Major;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_PK",length = 28)
    private String id;

    @Email
    @Column(name = "MEMBER_EMAIL",nullable = false,length =45)
    private String email;

    @Column(name = "MEMBER_NM",nullable = false,length =45)
    private String name;

    @Column(name="MEMBER_CELL_PHONE_NUMBER",length =15)
    private String phoneNumber;

    @Column(name= "MEMBER_STUDENT_ID",nullable = false,unique = true,length =45)
    private String studentID;

    @ManyToOne
    @JoinColumn(name = "MAJOR_MAJOR_PK",nullable = false)
    private Major major;

    @ManyToOne
    @JoinColumn(name = "RANK_RANK_PK",nullable = false)
    private Rank rank;

    @ManyToOne
    @JoinColumn(name = "ROLE_ROLE_PK",nullable = false)
    private Role role;

    @Column(name="MEMBER_YEAR",nullable = false)
    private Integer year;

    @Embedded
    @AttributeOverride(name = "createdDateTime",column = @Column(name = "MEMBER_CREATED_DTTM"))
    @AttributeOverride(name = "modifiedDataTime",column = @Column(name = "MEMBER_MODIFIED_DTTM"))
    private DefDateTime defDateTime;



}
