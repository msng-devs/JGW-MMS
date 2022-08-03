package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.domain.member.Member;
import com.jaramgroupware.mms.domain.member.MemberRepository;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberAddRequestServiceDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberResponseServiceDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberUpdateRequestServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponseServiceDto findById(String uid){

        Member targetMember = memberRepository.findMemberById(uid)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_TIMETABLE_ID));

        return new MemberResponseServiceDto(targetMember);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseServiceDto> findAll(){

        return memberRepository.findAllBy()
                .orElseThrow(()->new CustomException(ErrorCode.EMPTY_MEMBER))
                .stream()
                .map(MemberResponseServiceDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public String add(MemberAddRequestServiceDto memberAddRequestServiceDto){
        return memberRepository.save(memberAddRequestServiceDto.toEntity()).getId();
    }

    @Transactional
    public String delete(String id){
        Member targetMember =  memberRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_MEMBER_ID));

        memberRepository.delete(targetMember);

        return id;
    }

    @Transactional
    public MemberResponseServiceDto update(String id, MemberUpdateRequestServiceDto memberUpdateRequestServiceDto){

        Member targetMember = memberRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_MEMBER_ID));

        targetMember.update(memberUpdateRequestServiceDto.toEntity());

        memberRepository.save(targetMember);

        return new MemberResponseServiceDto(targetMember);
    }
}
