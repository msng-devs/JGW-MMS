package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceResponseControllerDto;
import com.jaramgroupware.mms.dto.attendanceType.controllerDto.AttendanceTypeResponseControllerDto;
import com.jaramgroupware.mms.dto.attendanceType.serviceDto.AttendanceTypeResponseServiceDto;
import com.jaramgroupware.mms.dto.rank.controllerDto.RankResponseControllerDto;
import com.jaramgroupware.mms.dto.rank.serviceDto.RankResponseServiceDto;
import com.jaramgroupware.mms.service.AttendanceTypeService;
import com.jaramgroupware.mms.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/attendanceType")
public class AttendanceTypeApiController {

    private final AttendanceTypeService attendanceTypeService;

    @GetMapping("{rankId}")
    public ResponseEntity<AttendanceTypeResponseControllerDto> getAttendanceTypeById(
            @PathVariable Integer rankId){

        AttendanceTypeResponseControllerDto result = attendanceTypeService.findById(rankId).toControllerDto();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceTypeResponseControllerDto>> getAttendanceTypeAll(){

        List<AttendanceTypeResponseControllerDto> results = attendanceTypeService.findAll()
                .stream().map(AttendanceTypeResponseServiceDto::toControllerDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }


}
