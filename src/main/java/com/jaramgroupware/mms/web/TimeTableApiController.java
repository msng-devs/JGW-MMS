package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.domain.attendanceType.AttendanceType;
import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.timeTable.TimeTable;
import com.jaramgroupware.mms.domain.timeTable.TimeTableSpecification;
import com.jaramgroupware.mms.domain.timeTable.TimeTableSpecificationBuilder;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceAddServiceDto;
import com.jaramgroupware.mms.dto.member.controllerDto.MemberFullResponseControllerDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberResponseServiceDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableAddRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableIdResponseControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableResponseControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableResponseServiceDto;
import com.jaramgroupware.mms.service.AttendanceService;
import com.jaramgroupware.mms.service.EventService;
import com.jaramgroupware.mms.service.MemberService;
import com.jaramgroupware.mms.service.TimeTableService;
import com.jaramgroupware.mms.utils.validation.PageableValid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/timetable")
public class TimeTableApiController {

    private final TimeTableService timeTableService;
    private final EventService eventService;
    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TimeTableSpecificationBuilder timeTableSpecificationBuilder;

    //TODO ???????????? ??????
    private final List<Integer> attendanceTarget = Arrays.asList(2,3);
    private final Integer defaultAttendanceType = 4;

    @PostMapping
    public ResponseEntity<TimeTableIdResponseControllerDto> addTimeTable(
            @RequestBody @Valid TimeTableAddRequestControllerDto timeTableAddRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        //timetable ??????
        Long id = timeTableService.add(
                timeTableAddRequestControllerDto.toServiceDto(
                        eventService.findById(timeTableAddRequestControllerDto.getEventID()).toEntity()
                ),uid);

        //?????? attendance??????
        List<MemberFullResponseControllerDto> targets =
                memberService.findAll(attendanceTarget.stream()
                        .map(target -> Rank.builder()
                                .id(target).build())
                        .collect(Collectors.toSet()))
                .stream()
                .map(MemberResponseServiceDto::toControllerDto)
                .collect(Collectors.toList());

        attendanceService.add(
                targets.stream()
                .map(target -> AttendanceAddServiceDto.builder()
                                .timeTable(TimeTable.builder().id(id).build())
                                .attendanceType(AttendanceType.builder().id(defaultAttendanceType).build())
                                .index("system??? ?????? ???????????? ?????????????????????.")
                                .member(Member.builder().id(target.getId()).build())
                                .build())
                .collect(Collectors.toList()), uid);

        return ResponseEntity.ok(new TimeTableIdResponseControllerDto(id));
    }

    @GetMapping("{timeTableId}")
    public ResponseEntity<TimeTableResponseControllerDto> findTimeTableById(
            @PathVariable Long timeTableId,
            @RequestHeader("user_uid") String uid){

        TimeTableResponseControllerDto result = timeTableService.findById(timeTableId).toControllerDto();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<TimeTableResponseControllerDto>> findTimeTableAll(
            @PageableDefault(page = 0,size = 1000,sort = "id",direction = Sort.Direction.DESC)
            @PageableValid(sortKeys =
                    {"id","name","event","startDateTime","endDateTime","createdDateTime","modifiedDateTime","createBy","modifiedBy"}
            ) Pageable pageable,
            @RequestParam(required = false) MultiValueMap<String, String> queryParam,
            @RequestHeader("user_uid") String uid){

        //limit ?????? ??? ??????
        int limit = queryParam.containsKey("limit") ? Integer.parseInt(Objects.requireNonNull(queryParam.getFirst("limit"))) : -1;

        //Specification ??????
        TimeTableSpecification spec = timeTableSpecificationBuilder.toSpec(queryParam);

        List<TimeTableResponseControllerDto> results;

        //limit true
        if(limit > 0){
            results = timeTableService.findAll(spec, PageRequest.of(0, limit, pageable.getSort()))
                    .stream()
                    .map(TimeTableResponseServiceDto::toControllerDto)
                    .collect(Collectors.toList());
        }

        else{
            results = timeTableService.findAll(spec,pageable)
                    .stream()
                    .map(TimeTableResponseServiceDto::toControllerDto)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(results);

    }

    @DeleteMapping("{timeTableId}")
    public ResponseEntity<TimeTableIdResponseControllerDto> delTimeTable(
            @PathVariable Long timeTableId,
            @RequestHeader("user_uid") String uid){

        timeTableService.delete(timeTableId);

        return ResponseEntity.ok(new TimeTableIdResponseControllerDto(timeTableId));
    }
    //test
    @PutMapping("{eventId}")
    public ResponseEntity<TimeTableResponseControllerDto> updateTimeTable(
            @PathVariable Long eventId,
            @RequestBody @Valid TimeTableUpdateRequestControllerDto timeTableUpdateRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        TimeTableResponseControllerDto result = timeTableService.update(eventId,timeTableUpdateRequestControllerDto.toServiceDto(),uid).toControllerDto();

        return ResponseEntity.ok(result);
    }
}
