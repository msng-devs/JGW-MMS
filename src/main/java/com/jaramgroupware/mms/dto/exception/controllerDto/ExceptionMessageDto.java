package com.jaramgroupware.mms.dto.exception.controllerDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@NoArgsConstructor
@Builder
public class ExceptionMessageDto {
    private HttpStatus status;
    private Integer type;
    private String title;
    private String detail;
}
