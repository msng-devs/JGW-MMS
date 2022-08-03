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
    INVALID_ATTENDANCE_ID(HttpStatus.NOT_FOUND,"INVALID_ATTENDANCE_ID","해당 attendance 를 찾을 수 없습니다. 코드를 다시 확인하세요",20108),
    INVALID_ATTENDANCE_TYPE_ID(HttpStatus.NOT_FOUND,"INVALID_ATTENDANCE_TYPE_ID","해당 attendance type 을 찾을 수 없습니다!",20107),
    INVALID_MEMBER_ID(HttpStatus.NOT_FOUND,"INVALID_MEMBER_ID","해당 member 을 찾을 수 없습니다!",20110),

    EMPTY_TIMETABLE(HttpStatus.NOT_FOUND,"EMPTY_TIMETABLE","timetable이 존재하지 않습니다.",20105),
    EMPTY_ATTENDANCE(HttpStatus.NOT_FOUND,"EMPTY_ATTENDANCE","ATTENDANCE가 존재하지 않습니다.",20109),
    EMPTY_EVENT(HttpStatus.NOT_FOUND,"EMPTY_EVENT","event가 존재하지 않습니다.",20104),
    EMPTY_MEMBER(HttpStatus.NOT_FOUND,"EMPTY_MEMBER","member가 존재하지 않습니다.",20111),

    ATTENDANCE_CODE_NOT_VALID(HttpStatus.FORBIDDEN,"ATTENDANCE_CODE_NOT_VALID","코드가 일치하지 않습니다!",10104),
    ALREADY_HAS_CODE(HttpStatus.BAD_REQUEST,"ALREADY_HAS_CODE","이미 코드를 가지고 있는 TimeTable입니다.",20106),
    CANNOT_CREATE_KEY(HttpStatus.INTERNAL_SERVER_ERROR,"CANNOT_CREATE_KEY","키 생성에 실패했습니다! 다시 시도해 주세요!",30101),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_SERVER_ERROR","알 수 없는 서버 에러입니다.",null);

    private final HttpStatus httpStatus;
    private final String title;
    private final String detail;
    private final Integer errorCode;
}
