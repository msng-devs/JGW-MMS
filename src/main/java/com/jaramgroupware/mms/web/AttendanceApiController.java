package com.jaramgroupware.mms.web;

import com.jaramgroupware.mms.service.AttendanceCodeService;
import com.jaramgroupware.mms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;
    private final AttendanceCodeService attendanceCodeService;


}
