package com.jaramgroupware.mms.domain.event;

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
@Builder
@Entity(name = "EVENT")
public class Event {

    @Id
    @Column(name = "EVENT_PK")
    private Long id;


    @Column(name = "EVENT_NM",nullable = false,length =50)
    private String name;

    @Column(name = "EVENT_INDEX")
    private String index;


    @Embedded
    @AttributeOverride(name = "createdDateTime",column = @Column(name = "EVENT_START_DTTM"))
    @AttributeOverride(name = "modifiedDataTime",column = @Column(name = "EVENT_END_DTTM"))
    private DefDateTime defDateTime;


}

