package com.jaramgroupware.mms.web;


import com.jaramgroupware.mms.dto.member.controllerDto.MemberAddRequestControllerDto;
import com.jaramgroupware.mms.dto.member.controllerDto.MemberResponseControllerDto;
import com.jaramgroupware.mms.dto.member.controllerDto.MemberUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberResponseServiceDto;
import com.jaramgroupware.mms.service.MemberService;
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
@RequestMapping("/api/v1/member")
public class MemberApiController {

    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<String> addAttendance(
            @RequestBody @Valid MemberAddRequestControllerDto memberAddRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Request Add new member, add = ({})",uid,memberAddRequestControllerDto.toString());

        String id = memberService.add(memberAddRequestControllerDto.toServiceDto());

        logger.info("UID = ({}) Successfully Add new member, memberId = ({})",uid,id);

        return new ResponseEntity<String>(id, HttpStatus.OK);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberResponseControllerDto> getAttendanceById(
            @PathVariable String memberId,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find member's info, memberID = ({})",uid,memberId);

        MemberResponseControllerDto result = memberService.findById(memberId).toControllerDto();

        logger.info("UID = ({}) Successfully find member, member = ({})",uid,result.toString());

        return new ResponseEntity<MemberResponseControllerDto>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseControllerDto>> getAttendanceAll(
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try find All Member's info",uid);

        List<MemberResponseControllerDto> results = memberService.findAll()
                .stream().map(MemberResponseServiceDto::toControllerDto)
                .collect(Collectors.toList());

        logger.info("UID = ({}) Successfully find All Member",uid);

        return new ResponseEntity<List<MemberResponseControllerDto>>(results, HttpStatus.OK);
    }


    @DeleteMapping("{memberID}")
    public ResponseEntity<String> delAttendance(
            @PathVariable String memberID,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try delete Member, Member = ({})",uid,memberID);

        memberService.delete(memberID);

        logger.info("UID = ({}) Successfully delete Member, Member = ({})",uid,memberID);

        return new ResponseEntity<String>(memberID, HttpStatus.OK);
    }

    @PutMapping("{memberID}")
    public ResponseEntity<MemberResponseControllerDto> updateAttendance(
            @PathVariable String memberID,
            @RequestBody @Valid MemberUpdateRequestControllerDto memberUpdateRequestControllerDto,
            @RequestHeader("user_uid") String uid){

        logger.info("UID = ({}) Try update Member, MemberId = ({})",uid,memberID);

        MemberResponseControllerDto result = memberService.update(memberID,memberUpdateRequestControllerDto.toServiceDto()).toControllerDto();

        logger.info("UID = ({}) Successfully update Member, Member = ({})",uid,result.toString());

        return new ResponseEntity<MemberResponseControllerDto>(result, HttpStatus.OK);
    }
}
