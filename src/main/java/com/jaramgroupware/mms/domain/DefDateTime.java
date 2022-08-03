package com.jaramgroupware.mms.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class DefDateTime {

    @Column(columnDefinition = "CREATED_DTTM")
    private LocalDateTime createdDateTime;

    @Column(columnDefinition = "MODIFIED_DTTM")
    private LocalDateTime modifiedDataTime;

}
