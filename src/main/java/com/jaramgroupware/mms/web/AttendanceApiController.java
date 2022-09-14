package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.domain.attendance.AttendanceSpecification;
import com.jaramgroupware.mms.domain.attendance.AttendanceSpecificationBuilder;
import com.jaramgroupware.mms.dto.attendance.controllerDto.*;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import com.jaramgroupware.mms.dto.general.controllerDto.MessageDto;
import com.jaramgroupware.mms.service.AttendanceService;
import com.jaramgroupware.mms.service.AttendanceTypeService;
import com.jaramgroupware.mms.utils.validation.PageableValid;
import com.jaramgroupware.mms.utils.validation.attendance.BulkAddAttendanceValid;
import com.jaramgroupware.mms.utils.validation.attendance.BulkUpdateAttendanceValid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AttendanceSpecificationBuilder attendanceSpecificationBuilder;


    @PostMapping
    public ResponseEntity<MessageDto> addAttendance(
            @RequestBody @NotEmpty @BulkAddAttendanceValid Set<@Valid AttendanceAddRequestControllerDto> dtos,
            @RequestHeader("user_uid") String uid){

        attendanceService.add(dtos.stream()
                .map(AttendanceAddRequestControllerDto::toServiceDto)
                .collect(Collectors.toList()), uid);

        return ResponseEntity.ok(new MessageDto("총 ("+dtos.size()+")개의 Attendance를 성공적으로 추가했습니다!"));
    }


    @GetMapping
    public ResponseEntity<List<AttendanceResponseControllerDto>> getAttendanceAll(
            @PageableDefault(page = 0,size = 1000,sort = "member",direction = Sort.Direction.DESC)
            @PageableValid(sortKeys =
                    {"attendanceType","member","timeTable","index","createdDateTime","modifiedDateTime","createBy","modifiedBy"}
                    ) Pageable pageable,
            @RequestParam(required = false) MultiValueMap<String, String> queryParam,
            @RequestHeader("user_uid") String uid){

        //limit 확인 및 추가
        int limit = queryParam.containsKey("limit") ? Integer.parseInt(Objects.requireNonNull(queryParam.getFirst("limit"))) : -1;

        //Specification 등록
        AttendanceSpecification spec = attendanceSpecificationBuilder.toSpec(queryParam);

        List<AttendanceResponseControllerDto> results;

        //limit true
        if(limit > 0){
            results = attendanceService.findAll(spec,PageRequest.of(0, limit, pageable.getSort()))
                    .stream()
                    .map(AttendanceResponseServiceDto::toControllerDto)
                    .collect(Collectors.toList());
        }

        else{
            results = attendanceService.findAll(spec,pageable)
                    .stream()
                    .map(AttendanceResponseServiceDto::toControllerDto)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(results);
    }

    @DeleteMapping
    public ResponseEntity<MessageDto> bulkDelAttendance(
            @RequestBody Set<@Valid AttendanceDeleteRequestControllerDto> dtos,
            @RequestHeader("user_uid") String uid){

        attendanceService.delete(dtos.stream().map(AttendanceDeleteRequestControllerDto::toId).collect(Collectors.toSet()));

        return ResponseEntity.ok(new MessageDto("총 ("+dtos.size()+")개의 Attendance를 성공적으로 삭제했습니다!"));
    }

    @PutMapping
    public ResponseEntity<MessageDto> bulkUpdateAttendance(
            @RequestBody @BulkUpdateAttendanceValid @NotNull Set<@Valid AttendanceBulkUpdateRequestControllerDto> dto,
            @RequestHeader("user_uid") String uid){

        attendanceService.update(dto.stream()
                        .map(AttendanceBulkUpdateRequestControllerDto::toServiceDto)
                        .collect(Collectors.toList()),uid);

        return ResponseEntity.ok(new MessageDto("총 ("+dto.size()+")개의 Attendance를 성공적으로 업데이트 했습니다!"));
    }
}
