package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceAddRequestControllerDto;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceResponseControllerDto;
import com.jaramgroupware.mms.dto.attendance.controllerDto.AttendanceUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import com.jaramgroupware.mms.dto.role.controllerDto.RoleResponseControllerDto;
import com.jaramgroupware.mms.dto.role.serviceDto.RoleResponseServiceDto;
import com.jaramgroupware.mms.service.AttendanceService;
import com.jaramgroupware.mms.service.RoleService;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/role")
public class RoleApiController {

    private final RoleService roleService;

    @GetMapping("{roleId}")
    public ResponseEntity<RoleResponseControllerDto> getRoleById(
            @PathVariable Integer roleId){

        RoleResponseControllerDto result = roleService.findById(roleId).toControllerDto();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseControllerDto>> getRoleAll(){

        List<RoleResponseControllerDto> results = roleService.findAll()
                .stream().map(RoleResponseServiceDto::toControllerDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }


}
