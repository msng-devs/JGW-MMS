package com.jaramgroupware.mms.domain.penalty;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "PENALTY")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PENALTY_PK")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_MEMBER_PK",nullable = false)
    private Member targetMember;

    @ManyToOne
    @JoinColumn(name = "MEMBER_MEMBER_PK_GIVER",nullable = false)
    private Member giverMember;

    @Embedded
    @AttributeOverride(name = "createdDateTime",column = @Column(name = "PENALTY_CREATED_DTTM"))
    @AttributeOverride(name = "modifiedDataTime",column = @Column(name = "PENALTY_MODIFIED_DTTM"))
    private DefDateTime defDateTime;

    @Column(name = "PENALTY_TYPE",nullable = false)
    private boolean type;

    @Column(name = "PENALTY_REASON",nullable = false)
    private String reason;

}
