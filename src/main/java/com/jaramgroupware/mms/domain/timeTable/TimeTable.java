package com.jaramgroupware.mms.domain.timeTable;

import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.event.Event;
import com.jaramgroupware.mms.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TIMETABLE")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMETABLE_PK")
    private Long id;

    @Column(name = "TIMETABLE_NM",length = 50)
    private String name;

    @Embedded
    @AttributeOverride(name = "createdDateTime",column = @Column(name = "TIMETABLE_START_DTTM"))
    @AttributeOverride(name = "modifiedDataTime",column = @Column(name = "TIMETABLE_END_DTTM"))
    private DefDateTime defDateTime;

    @ManyToOne
    @JoinColumn(name = "EVENT_EVENT_PK",nullable = false)
    private Event event;

    public void update(TimeTable timeTable){
        name = timeTable.getName();
        defDateTime = timeTable.getDefDateTime();
    }
}
