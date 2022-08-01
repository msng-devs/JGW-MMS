package com.jaramgroupware.mms.domain.attendanceType;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@Entity(name = "ATTENDANCE_TYPE")
public class AttendanceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDANCE_TYPE_PK")
    private Integer id;

    @Column(name = "ATTENDANCE_TYPE_NAME",nullable = false,unique = true,length = 45)
    private String name;


}
