package com.jaramgroupware.mms.domain.rank;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@Entity(name = "RANK")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RANK_PK")
    private Integer id;

    @Column(name = "RANK_NM",nullable = false,unique = true,length = 45)
    private String name;


}
