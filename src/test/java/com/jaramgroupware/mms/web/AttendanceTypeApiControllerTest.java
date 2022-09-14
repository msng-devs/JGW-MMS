package com.jaramgroupware.mms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaramgroupware.mms.TestUtils;
import com.jaramgroupware.mms.dto.attendanceType.serviceDto.AttendanceTypeResponseServiceDto;
import com.jaramgroupware.mms.service.AttendanceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class AttendanceTypeApiControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private AttendanceTypeService attendanceTypeService;

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
    void getAttendanceTypeById() throws Exception {
        //given
        Integer attendanceTypeID = 1;

        AttendanceTypeResponseServiceDto attendanceTypeResponseServiceDto = AttendanceTypeResponseServiceDto
                .builder()
                .id(attendanceTypeID)
                .name("test")
                .build();

        doReturn(attendanceTypeResponseServiceDto).when(attendanceTypeService).findById(attendanceTypeID);


        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/attendanceType/{attendanceTypeID}",attendanceTypeID)
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("attendanceType-get-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("attendanceTypeID").description("대상 attendanceType의 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("대상 attendanceType의 id"),
                                fieldWithPath("name").description("대상 attendanceType의 name")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(attendanceTypeResponseServiceDto.toControllerDto())));
        verify(attendanceTypeService).findById(attendanceTypeID);
    }

    @Test
    void getAttendanceTypeAll() throws Exception {
        //given
        List<AttendanceTypeResponseServiceDto> targetAttendanceTypeList = new ArrayList<AttendanceTypeResponseServiceDto>();

        AttendanceTypeResponseServiceDto AttendanceTypeResponseServiceDto1 = AttendanceTypeResponseServiceDto
                .builder()
                .id(1)
                .name("test")
                .build();
        targetAttendanceTypeList.add(AttendanceTypeResponseServiceDto1);

        AttendanceTypeResponseServiceDto AttendanceTypeResponseServiceDto2 = AttendanceTypeResponseServiceDto
                .builder()
                .id(2)
                .name("test")
                .build();
        targetAttendanceTypeList.add(AttendanceTypeResponseServiceDto2);

        doReturn(targetAttendanceTypeList).when(attendanceTypeService).findAll();

        //when
        ResultActions result = mvc.perform(
                get("/api/v1/attendanceType")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("attendanceType-get-multiple",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").description("대상 attendanceType의 id"),
                                fieldWithPath("[].name").description("대상 attendanceType의 name")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        targetAttendanceTypeList.stream()
                                .map(AttendanceTypeResponseServiceDto::toControllerDto)
                                .collect(Collectors.toList()))));
        verify(attendanceTypeService).findAll();
    }
}