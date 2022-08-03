package com.jaramgroupware.mms.domain.attendance;


import com.jaramgroupware.mms.domain.DefDateTime;
import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ATTENDANCE")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDANCE_PK")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ATTENDANCE_TYPE_ATTENDANCE_TYPE_PK",nullable = false)
    private AttendanceType attendanceType;

    @ManyToOne
    @JoinColumn(name = "MEMBER_MEMBER_PK",nullable = false)
    private Member member;

    @Embedded
    @AttributeOverride(name = "createdDateTime",column = @Column(name = "ATTENDANCE_CREATED_DTTM"))
    @AttributeOverride(name = "modifiedDataTime",column = @Column(name = "ATTENDANCE_MODIFIED_DTTM"))
    private DefDateTime defDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIMETABLE_TIMETABLE_PK",nullable = false)
    private TimeTable timeTable;

    @Column(name = "ATTENDANCE_INDEX")
    private String index;
}
