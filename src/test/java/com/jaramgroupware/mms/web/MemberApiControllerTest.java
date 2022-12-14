package com.jaramgroupware.mms.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaramgroupware.mms.TestUtils;
import com.jaramgroupware.mms.domain.rank.Rank;
import com.jaramgroupware.mms.domain.role.Role;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import com.jaramgroupware.mms.dto.major.serviceDto.MajorResponseServiceDto;
import com.jaramgroupware.mms.dto.member.controllerDto.*;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberAddRequestServiceDto;
import com.jaramgroupware.mms.dto.member.serviceDto.MemberResponseServiceDto;
import com.jaramgroupware.mms.dto.rank.serviceDto.RankResponseServiceDto;
import com.jaramgroupware.mms.dto.role.serviceDto.RoleResponseServiceDto;
import com.jaramgroupware.mms.service.MajorService;
import com.jaramgroupware.mms.service.MemberService;
import com.jaramgroupware.mms.service.RankService;
import com.jaramgroupware.mms.service.RoleService;
import com.jaramgroupware.mms.web.MemberApiController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;
import java.util.stream.Collectors;

import static com.jaramgroupware.mms.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import static org.mockito.BDDMockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private MemberService memberService;

    @MockBean
    private MajorService majorService;

    @MockBean
    private RankService rankService;

    @MockBean
    private RoleService roleService;

    private final TestUtils testUtils = new TestUtils();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerMember() throws Exception {
        //given
        MemberRegisterRequestControllerDto memberRegisterRequestControllerDto = MemberRegisterRequestControllerDto.builder()
                .id(testUtils.getTestMember().getId())
                .email(testUtils.getTestMember().getEmail())
                .leaveAbsence(testUtils.getTestMember().isLeaveAbsence())
                .majorId(testUtils.getTestMember().getMajor().getId())
                .phoneNumber(testUtils.getTestMember().getPhoneNumber())
                .name(testUtils.getTestMember().getName())
                .studentID(testUtils.getTestMember().getStudentID())
                .dateOfBirth(testUtils.getTestDate())
                .build();

        doReturn(memberRegisterRequestControllerDto.getId()).when(memberService).add(any(MemberAddRequestServiceDto.class),anyString());

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.post("/api/v1/member/register")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterRequestControllerDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-register",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("?????? member??? UID (firebase uid)").attributes(field("constraints", "28??? firebase uid")),
                                fieldWithPath("email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("name").description("?????? member??? name(??????)"),
                                fieldWithPath("phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("date_of_birth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                                fieldWithPath("major_id").description("?????? member??? major id").attributes(field("constraints", "Major (object)??? ID(PK)"))
                        ),
                        responseFields(
                                fieldWithPath("member_id").description(testUtils.getTestMember().getId())
                        )
                ));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.member_id").value(memberRegisterRequestControllerDto.getId()));
        verify(memberService).add(any(MemberAddRequestServiceDto.class),anyString());


    }

    @Test
    void addMember() throws Exception {
        //given
        Set<MemberAddRequestControllerDto> dto = new HashSet<>();
        MemberAddRequestControllerDto memberAddRequestControllerDto = MemberAddRequestControllerDto.builder()
                .id(testUtils.getTestMember().getId())
                .email(testUtils.getTestMember().getEmail())
                .leaveAbsence(testUtils.getTestMember().isLeaveAbsence())
                .majorId(testUtils.getTestMember().getMajor().getId())
                .phoneNumber(testUtils.getTestMember().getPhoneNumber())
                .name(testUtils.getTestMember().getName())
                .rankId(testUtils.getTestMember().getRank().getId())
                .roleId(testUtils.getTestMember().getRole().getId())
                .studentID(testUtils.getTestMember().getStudentID())
                .year(testUtils.getTestMember().getYear())
                .dateOfBirth(testUtils.getTestDate())
                .build();
        dto.add(memberAddRequestControllerDto);

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.post("/api/v1/member")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("[].id").description("?????? member??? UID (firebase uid)").attributes(field("constraints", "28??? firebase uid")),
                                fieldWithPath("[].email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("[].name").description("?????? member??? name(??????)"),
                                fieldWithPath("[].phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("[].student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("[].year").description("?????? member??? ??????"),
                                fieldWithPath("[].leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("[].date_of_birth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                                fieldWithPath("[].major_id").description("?????? member??? major id").attributes(field("constraints", "Major (object)??? ID(PK)")),
                                fieldWithPath("[].rank_id").description("?????? member??? rank id").attributes(field("constraints", "Rank (object)??? ID(PK)")),
                                fieldWithPath("[].role_id").description("?????? member??? role id").attributes(field("constraints", "Role (object)??? ID(PK)"))
                        ),
                        responseFields(
                                fieldWithPath("message").description("????????? ????????? Member??? ???")
                        )
                ));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("??? (1)?????? Member??? ??????????????? ??????????????????!"));
        verify(memberService).add(anyList(),anyString());
    }
    @Test
    void getMemberByIdWithAdminSelf() throws Exception {
        //given
        String memberID = testUtils.getTestMember().getId();

        MemberResponseServiceDto targetMemberDto = new MemberResponseServiceDto(testUtils.getTestMember());

        doReturn(targetMemberDto).when(memberService).findById(memberID);


        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/member/{memberID}",memberID)
                        .header("user_uid",testUtils.getTestUid())
                        .header("user_role_id",4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-get-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberID").description("?????? Member??? uid(firebase uid)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("?????? member??? UID (firebase uid)").attributes(field("constraints", "28??? firebase uid")),
                                fieldWithPath("email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("name").description("?????? member??? name(??????)"),
                                fieldWithPath("phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("year").description("?????? member??? ??????"),
                                fieldWithPath("leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("dateofbirth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                                fieldWithPath("major_id").description("?????? member??? major(object)??? ID"),
                                fieldWithPath("major_name").description("?????? member??? major(object)??? ??????"),
                                fieldWithPath("rank_id").description("?????? member??? rank(object)??? ID"),
                                fieldWithPath("rank_name").description("?????? member??? rank(object)??? ??????"),
                                fieldWithPath("role_id").description("?????? member??? role(object)??? ID"),
                                fieldWithPath("role_name").description("?????? member??? role(object)??? ??????"),
                                fieldWithPath("created_date_time").description("?????? member??? ????????? ??????"))
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(targetMemberDto.toControllerDto())));
        verify(memberService).findById(memberID);
    }

    @Test
    void getMemberByIdWithAdminNotSelf() throws Exception {
        //given
        String memberID = testUtils.getTestMember().getId();

        MemberResponseServiceDto targetMemberDto = new MemberResponseServiceDto(testUtils.getTestMember());

        doReturn(targetMemberDto).when(memberService).findById(memberID);


        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/member/{memberID}",memberID)
                        .header("user_uid",testUtils.getTestMember2().getId())
                        .header("user_role_id",4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(targetMemberDto.toControllerDto())));
        verify(memberService).findById(memberID);
    }

    @Test
    void getMemberByIdWithNoAdminForSelf() throws Exception {
        //given
        String memberID = testUtils.getTestMember().getId();

        MemberResponseServiceDto targetMemberDto = new MemberResponseServiceDto(testUtils.getTestMember());

        doReturn(targetMemberDto).when(memberService).findById(memberID);


        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/member/{memberID}",memberID)
                        .header("user_uid",testUtils.getTestUid())
                        .header("user_role_id",3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(targetMemberDto.toControllerDto())));
        verify(memberService).findById(memberID);
    }

    //TODO Docs??? ??????
    @Test
    void getMemberByIdWithNoAdminReturnTiny() throws Exception {
        //given
        String memberID = testUtils.getTestUid();

        MemberResponseServiceDto targetMemberDto = new MemberResponseServiceDto(testUtils.getTestMember());

        doReturn(targetMemberDto).when(memberService).findById(memberID);


        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/member/{memberID}",memberID)
                        .header("user_uid",testUtils.getTestMember2().getId())
                        .header("user_role_id",3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-get-single-tiny",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberID").description("?????? Member??? uid(firebase uid)")
                        ),
                        responseFields(
                                fieldWithPath("email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("name").description("?????? member??? name(??????)"),
                                fieldWithPath("ent_student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("year").description("?????? member??? ??????"),
                                fieldWithPath("leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("major_id").description("?????? member??? major(object)??? ID"),
                                fieldWithPath("major_name").description("?????? member??? major(object)??? ??????"),
                                fieldWithPath("rank_id").description("?????? member??? rank(object)??? ID"),
                                fieldWithPath("rank_name").description("?????? member??? rank(object)??? ??????"))
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(targetMemberDto.toControllerDto().toTiny())));
        verify(memberService).findById(memberID);
    }

    @Test
    void getMemberAll() throws Exception {
        //given
        List<MemberResponseServiceDto> targetMemberList = new ArrayList<MemberResponseServiceDto>();

        MemberResponseServiceDto targetMemberDto1 = new MemberResponseServiceDto(testUtils.getTestMember());
        targetMemberList.add(targetMemberDto1);

        MemberResponseServiceDto targetMemberDto2 = new MemberResponseServiceDto(testUtils.getTestMember2());
        targetMemberList.add(targetMemberDto2);


        doReturn(targetMemberList).when(memberService).findAll(any(),any());

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/member/")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-get-multiple",
        responseFields(
                fieldWithPath("[].id").description("?????? member??? UID (firebase uid)").attributes(field("constraints", "28??? firebase uid")),
                fieldWithPath("[].email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                fieldWithPath("[].name").description("?????? member??? name(??????)"),
                fieldWithPath("[].phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                fieldWithPath("[].student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                fieldWithPath("[].year").description("?????? member??? ??????"),
                fieldWithPath("[].leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                fieldWithPath("[].dateofbirth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                fieldWithPath("[].major_id").description("?????? member??? major(object) ??? ID"),
                fieldWithPath("[].major_name").description("?????? member??? major(object) ??? ??????"),
                fieldWithPath("[].rank_id").description("?????? member??? rank(object) ??? ID"),
                fieldWithPath("[].rank_name").description("?????? member??? rank(object) ??? ??????"),
                fieldWithPath("[].role_id").description("?????? member??? role(object)??? ID"),
                fieldWithPath("[].role_name").description("?????? member??? role(object)??? ??????"),
                fieldWithPath("[].created_date_time").description("?????? member??? ?????????(?????????)??????")
                )));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(targetMemberList.stream()
                                .map(MemberResponseServiceDto::toControllerDto)
                                .collect(Collectors.toList()))));
        verify(memberService).findAll(any(),any());
    }

    @Test
    void delMember() throws Exception {
        //given
        String memberID = testUtils.getTestMember().getId();

        doReturn(memberID).when(memberService).delete(memberID);

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.delete("/api/v1/member/{memberID}",memberID)
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-del-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberID").description("?????? Member??? uid(firebase uid)")
                        ),
                        responseFields(
                                fieldWithPath("member_id").description("?????? member??? UID (firebase uid)")
                        )
                ));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.member_id").value(memberID));
        verify(memberService).delete(memberID);
    }
    @Test
    void bulkDelete() throws Exception{
        //given
        Set<String> ids = new HashSet<>();
        ids.add(testUtils.getTestMember().getId());
        ids.add(testUtils.getTestMember2().getId());

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.delete("/api/v1/member")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MemberBulkDeleteRequestControllerDto.builder().MemberIDs(ids).build())))
                .andDo(print())
                .andDo(document("member-del-bulk",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("member_ids").description("?????? Member??? uid(firebase uid)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("????????? Member??? ??????")
                        )
                ));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("??? (2)?????? Member??? ??????????????? ??????????????????!"));
        verify(memberService).delete(anySet());
    }
    @Test
    void updateMember() throws Exception {
        //given
        String memberID = testUtils.getTestMember().getId();

        MemberUpdateRequestControllerDto testMemberDto = MemberUpdateRequestControllerDto.builder()
                .email(testUtils.getTestMember().getEmail())
                .leaveAbsence(testUtils.getTestMember().isLeaveAbsence())
                .majorId(testUtils.getTestMember().getMajor().getId())
                .phoneNumber(testUtils.getTestMember().getPhoneNumber())
                .name(testUtils.getTestMember().getName())
                .rankId(testUtils.getTestMember().getRank().getId())
                .roleId(testUtils.getTestMember().getRole().getId())
                .studentID(testUtils.getTestMember().getStudentID())
                .year(testUtils.getTestMember().getYear())
                .dateOfBirth(testUtils.getTestDate())
                .build();

        MemberResponseServiceDto testMemberResult = new MemberResponseServiceDto(testUtils.getTestMember());

        doReturn(new RankResponseServiceDto(testUtils.getTestRank())).when(rankService).findById(testMemberDto.getRankId());
        doReturn(new MajorResponseServiceDto(testUtils.getTestMajor())).when(majorService).findById(testMemberDto.getMajorId());
        doReturn(new RoleResponseServiceDto(testUtils.getTestRole())).when(roleService).findById(testMemberDto.getRoleId());
        doReturn(testMemberResult).when(memberService).update(memberID,testMemberDto.toServiceDto(
                testUtils.getTestMajor(),
                testUtils.getTestRank(),
                testUtils.getTestRole()),testUtils.getTestUid());

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.put("/api/v1/member/{memberID}",memberID)
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMemberDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-update-single",
                        pathParameters(
                                parameterWithName("memberID").description("?????? Member??? uid(firebase uid)")
                        ),
                        requestFields(
                                fieldWithPath("email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("name").description("?????? member??? name(??????)"),
                                fieldWithPath("phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("year").description("?????? member??? ??????"),
                                fieldWithPath("leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("date_of_birth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                                fieldWithPath("major_id").description("?????? member??? major id").attributes(field("constraints", "Major (object)??? ID(PK)")),
                                fieldWithPath("rank_id").description("?????? member??? rank id").attributes(field("constraints", "Rank (object)??? ID(PK)")),
                                fieldWithPath("role_id").description("?????? member??? role id").attributes(field("constraints", "Role (object)??? ID(PK)"))
                        ),
                        responseFields(
                                fieldWithPath("id").description("?????? member??? UID (firebase uid)").attributes(field("constraints", "28??? firebase uid")),
                                fieldWithPath("email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("name").description("?????? member??? name(??????)"),
                                fieldWithPath("phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("year").description("?????? member??? ??????"),
                                fieldWithPath("leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("dateofbirth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                                fieldWithPath("created_date_time").description("?????? member??? ????????????(????????????)"),
                                fieldWithPath("email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("name").description("?????? member??? name(??????)"),
                                fieldWithPath("phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("year").description("?????? member??? ??????"),
                                fieldWithPath("leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("major_id").description("?????? member??? major id").attributes(field("constraints", "Major (object)??? ID(PK)")),
                                fieldWithPath("major_name").description("?????? member??? major name").attributes(field("constraints", "Major (object)??? Name")),
                                fieldWithPath("rank_id").description("?????? member??? rank id").attributes(field("constraints", "Rank (object)??? ID(PK)")),
                                fieldWithPath("rank_name").description("?????? member??? rank name").attributes(field("constraints", "Rank (object)??? Name")),
                                fieldWithPath("role_id").description("?????? member??? role id").attributes(field("constraints", "Role (object)??? ID(PK)")),
                                fieldWithPath("role_name").description("?????? member??? role name").attributes(field("constraints", "Role (object)??? Name"))
                        )));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testMemberResult.toControllerDto())));
        verify(memberService).update(memberID,testMemberDto.toServiceDto(
                testUtils.getTestMajor(),
                testUtils.getTestRank(),
                testUtils.getTestRole()),testUtils.getTestUid());
    }

    @Test
    void updateAll() throws Exception {
        //given
        Set<MemberBulkUpdateRequestControllerDto> dtos = new HashSet<>();

        MemberBulkUpdateRequestControllerDto testMemberDto = MemberBulkUpdateRequestControllerDto.builder()
                .email(testUtils.getTestMember().getEmail())
                .leaveAbsence(testUtils.getTestMember().isLeaveAbsence())
                .majorId(testUtils.getTestMember().getMajor().getId())
                .phoneNumber(testUtils.getTestMember().getPhoneNumber())
                .name(testUtils.getTestMember().getName())
                .rankId(testUtils.getTestMember().getRank().getId())
                .roleId(testUtils.getTestMember().getRole().getId())
                .studentID(testUtils.getTestMember().getStudentID())
                .year(testUtils.getTestMember().getYear())
                .dateOfBirth(testUtils.getTestDate())
                .id(testUtils.getTestMember().getId())
                .build();
        dtos.add(testMemberDto);

        MemberBulkUpdateRequestControllerDto testMemberDto2 = MemberBulkUpdateRequestControllerDto.builder()
                .email(testUtils.getTestMember2().getEmail())
                .leaveAbsence(testUtils.getTestMember2().isLeaveAbsence())
                .majorId(testUtils.getTestMember2().getMajor().getId())
                .phoneNumber(testUtils.getTestMember2().getPhoneNumber())
                .name(testUtils.getTestMember2().getName())
                .rankId(testUtils.getTestMember2().getRank().getId())
                .roleId(testUtils.getTestMember2().getRole().getId())
                .studentID(testUtils.getTestMember2().getStudentID())
                .year(testUtils.getTestMember2().getYear())
                .dateOfBirth(testUtils.getTestDate2())
                .id(testUtils.getTestMember2().getId())
                .build();
        dtos.add(testMemberDto2);

        MemberResponseServiceDto testMemberResult = new MemberResponseServiceDto(testUtils.getTestMember());

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.put("/api/v1/member")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtos))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member-update-bulk",
                        requestFields(
                                fieldWithPath("[].id").description("?????? member??? id"),
                                fieldWithPath("[].email").description("?????? member??? email").attributes(field("constraints", "email ????????? ????????????. regrex : [0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$")),
                                fieldWithPath("[].name").description("?????? member??? name(??????)"),
                                fieldWithPath("[].phone_number").description("?????? member??? ????????? ??????").attributes(field("constraints", "- ??? ?????? ?????? ??????. regrex : (^$|[0-9]{11})")),
                                fieldWithPath("[].student_id").description("?????? member??? student id(??????)").attributes(field("constraints", "10????????? student id")),
                                fieldWithPath("[].year").description("?????? member??? ??????"),
                                fieldWithPath("[].leave_absence").description("?????? member??? ?????? ??????").attributes(field("constraints", "true : ?????? , false : ?????? ??????")),
                                fieldWithPath("[].date_of_birth").description("?????? member??? ????????????").attributes(field("constraints", "yyyy-MM-dd format??? ????????????.")),
                                fieldWithPath("[].major_id").description("?????? member??? major id").attributes(field("constraints", "Major (object)??? ID(PK)")),
                                fieldWithPath("[].rank_id").description("?????? member??? rank id").attributes(field("constraints", "Rank (object)??? ID(PK)")),
                                fieldWithPath("[].role_id").description("?????? member??? role id").attributes(field("constraints", "Role (object)??? ID(PK)"))
                        ),
                        responseFields(
                                fieldWithPath("message").description("???????????? ??? Member??? ??????")
                        )));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("??? (2)?????? Member??? ??????????????? ????????????????????????!"));
        verify(memberService).update(any(),any());
    }
}