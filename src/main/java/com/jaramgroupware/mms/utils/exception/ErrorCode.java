package com.jaramgroupware.mms.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TIMETABLE_ID(HttpStatus.NOT_FOUND,"INVALID_TIMETABLE_ID","해당 timetable의 id를 찾을 수 없습니다. timetable의 id를 다시 확인하세요.",20100),
    INVALID_EVENT_ID(HttpStatus.NOT_FOUND,"INVALID_EVENT_ID","해당 event의 id를 찾을 수 없습니다. event의 id를 다시 확인하세요.",20103),
    INVALID_ATTENDANCE_CODE(HttpStatus.NOT_FOUND,"INVALID_TIMETABLE_ID","해당 code를 찾을 수 없습니다. 코드를 다시 확인하세요",20102),
    INVALID_MINUTE_ID(HttpStatus.BAD_REQUEST,"INVALID_MINUTE_ID","해당 분의 형식이 잘못됬습니다. 반드시 분은 1분이상이여야 합니다.",20101),
    CANNOT_CREATE_KEY(HttpStatus.INTERNAL_SERVER_ERROR,"CANNOT_CREATE_KEY","키 생성에 실패했습니다! 다시 시도해 주세요!",30101),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_SERVER_ERROR","알 수 없는 서버 에러입니다.",null);

    private final HttpStatus httpStatus;
    private final String title;
    private final String detail;
    private final Integer errorCode;
}
