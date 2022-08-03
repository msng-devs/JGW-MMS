package com.jaramgroupware.mms.domain.major;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "MAJOR")
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAJOR_PK")
    private Integer id;

    @Column(name = "MAJOR_NM",nullable = false,unique = true,length = 45)
    private String name;


}
