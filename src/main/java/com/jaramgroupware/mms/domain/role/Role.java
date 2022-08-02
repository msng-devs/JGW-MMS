package com.jaramgroupware.mms.domain.role;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@Entity(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_PK")
    private Integer id;

    @Column(name = "ROLE_NM",nullable = false,unique = true,length = 45)
    private String name;


}
