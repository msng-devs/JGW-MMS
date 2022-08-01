package com.jaramgroupware.mms.domain.timeTable;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@Entity(name = "TIMETABLE")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMETABLE_PK")
    private Long id;

    @Embedded
    @AttributeOverride(name = "createdDateTime",column = @Column(name = "TIMETABLE_START_DTTM"))
    @AttributeOverride(name = "modifiedDataTime",column = @Column(name = "TIMETABLE_END_DTTM"))
    private DefDateTime defDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_EVENT_PK",nullable = false)
    private Event event;
}
