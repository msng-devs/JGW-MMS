package com.jaramgroupware.mms.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaramgroupware.mms.TestUtils;
import com.jaramgroupware.mms.dto.attendance.serviceDto.AttendanceResponseServiceDto;
import com.jaramgroupware.mms.dto.event.serviceDto.EventResponseServiceDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableAddRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableIdResponseControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableResponseControllerDto;
import com.jaramgroupware.mms.dto.timeTable.controllerDto.TimeTableUpdateRequestControllerDto;
import com.jaramgroupware.mms.dto.timeTable.serviceDto.TimeTableResponseServiceDto;
import com.jaramgroupware.mms.service.EventService;
import com.jaramgroupware.mms.service.TimeTableService;
import com.jaramgroupware.mms.web.TimeTableApiController;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.jaramgroupware.mms.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class TimeTableApiControllerTest {


    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private TimeTableService timeTableService;

    @MockBean
    private EventService eventService;

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
    void addTimeTable() throws Exception {
        //given
        TimeTableAddRequestControllerDto testTimeTableDto = TimeTableAddRequestControllerDto.builder()
                .startDateTime(testUtils.getTestDateTime())
                .endDateTime(testUtils.getTestDateTime())
                .eventID(testUtils.getTestEvent().getId())
                .name("test")
                .build();

        doReturn(new EventResponseServiceDto(testUtils.getTestEvent())).when(eventService).findById(testTimeTableDto.getEventID());
        doReturn(1L).when(timeTableService).add(testTimeTableDto.toServiceDto(testUtils.getTestEvent()), testUtils.getTestUid());

        //when
        ResultActions result = mvc.perform(
                post("/api/v1/timetable")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTimeTableDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("timetable-add-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("?????? timetable??? ??????").attributes(field("constraints", "?????? 1???, ?????? 50???")),
                                fieldWithPath("start_date_time").description("?????? timetable??? ?????? ??????"),
                                fieldWithPath("end_date_time").description("?????? timetable??? ?????? ??????").attributes(field("constraints", "?????? ????????? ?????? ???????????? ??? ????????? ??????????????????.")),
                                fieldWithPath("event_id").description("?????? timetable??? ?????? event(object)??? id").attributes(field("constraints", "Event(object)??? ID"))
                        ),
                        responseFields(
                                fieldWithPath("time_table_id").description("????????? ????????? timetable??? ID ")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.time_table_id").value("1"));
        verify(timeTableService).add(testTimeTableDto.toServiceDto(testUtils.getTestEvent()), testUtils.getTestUid());
    }

    @Test
    void findTimeTableById() throws Exception {
        //given
        Long timeTableId = 1L;

        TimeTableResponseServiceDto eventResponseServiceDto = new TimeTableResponseServiceDto(testUtils.getTestTimeTable());

        doReturn(eventResponseServiceDto).when(timeTableService).findById(timeTableId);


        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/timetable/{timeTableId}",timeTableId)
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("timetable-get-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("timeTableId").description("?????? timetable??? id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("?????? timetable??? ID"),
                                fieldWithPath("name").description("?????? timetable??? name"),
                                fieldWithPath("start_date_time").description("?????? timetable??? ?????? date time"),
                                fieldWithPath("end_date_time").description("?????? timetable??? ?????? date time"),
                                fieldWithPath("created_by").description("?????? timetable??? ????????? ???"),
                                fieldWithPath("modify_by").description("?????? timetable??? ??????????????? ????????? ???"),
                                fieldWithPath("created_date_time").description("?????? timetable??? ????????? ??????"),
                                fieldWithPath("modified_date_time").description("?????? timetable??? ??????????????? ????????? ??????"),
                                fieldWithPath("event_id").description("?????? timetable??? ?????? event(object) ID"),
                                fieldWithPath("event_name").description("?????? timeTable??? ?????? event(object) ??? name")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventResponseServiceDto.toControllerDto())));
        verify(timeTableService).findById(timeTableId);
    }

    @Test
    void findTimeTableAll() throws Exception {
        //given
        List<TimeTableResponseServiceDto> targetTimeTableList = new ArrayList<TimeTableResponseServiceDto>();

        TimeTableResponseServiceDto testTimeTable = new TimeTableResponseServiceDto(testUtils.getTestTimeTable());
        targetTimeTableList.add(testTimeTable);


        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("timeTableID",testTimeTable.getEventID().toString());
        queryParam.add("modifiedBy",testTimeTable.getModifyBy());
        queryParam.add("createBy",testTimeTable.getCreatedBy());
        queryParam.add("startDateTime",testTimeTable.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        queryParam.add("endDateTime",testTimeTable.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        queryParam.add("startCreatedDateTime",testTimeTable.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        queryParam.add("endCreatedDateTime",testTimeTable.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        queryParam.add("startModifiedDateTime",testTimeTable.getModifiedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        queryParam.add("endModifiedDateTime",testTimeTable.getModifiedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        doReturn(targetTimeTableList).when(timeTableService).findAll(any(),any());

        //when
        ResultActions result = mvc.perform(
                get("/api/v1/timetable")
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParams(queryParam))
                .andDo(print())
                .andDo(document("timetable-get-multiple",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").description("?????? timetable??? ID"),
                                fieldWithPath("[].name").description("?????? timetable??? name"),
                                fieldWithPath("[].start_date_time").description("?????? timetable??? ?????? date time"),
                                fieldWithPath("[].end_date_time").description("?????? timetable??? ?????? date time"),
                                fieldWithPath("[].created_by").description("?????? timetable??? ????????? ???"),
                                fieldWithPath("[].modify_by").description("?????? timetable??? ??????????????? ????????? ???"),
                                fieldWithPath("[].created_date_time").description("?????? timetable??? ????????? ??????"),
                                fieldWithPath("[].modified_date_time").description("?????? timetable??? ??????????????? ????????? ??????"),
                                fieldWithPath("[].event_id").description("?????? timetable??? ?????? event(object)??? ID"),
                                fieldWithPath("[].event_name").description("?????? timeTable??? ?????? event(Object)??? Name")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(
                                targetTimeTableList.stream()
                                        .map(TimeTableResponseServiceDto::toControllerDto)
                                        .collect(Collectors.toList()))));
        verify(timeTableService).findAll(any(),any());
    }

    @Test
    void delTimeTable() throws Exception {
        //given
        Long timeTableId = 1L;

        doReturn(1L).when(timeTableService).delete(timeTableId);

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.delete("/api/v1/timetable/{timeTableId}",timeTableId)
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("timetable-del-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("timeTableId").description("????????? timetable??? id")
                        ),
                        responseFields(
                                fieldWithPath("time_table_id").description("????????? timetable??? ID")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.time_table_id").value("1"));
        verify(timeTableService).delete(timeTableId);
    }

    @Test
    void updateTimeTable() throws Exception {
        //given
        Long timeTableId = 1L;

        TimeTableUpdateRequestControllerDto timeTableUpdateRequestServiceDto = TimeTableUpdateRequestControllerDto.builder()
                .startDateTime(testUtils.getTestDateTime())
                .endDateTime(testUtils.getTestDateTime())
                .name(testUtils.getTestTimeTable().getName())
                .build();

        TimeTableResponseServiceDto testTimeTableResult = new TimeTableResponseServiceDto(testUtils.getTestTimeTable());

        doReturn(testTimeTableResult).when(timeTableService).update(timeTableId,timeTableUpdateRequestServiceDto.toServiceDto(),testUtils.getTestUid());

        //when
        ResultActions result = mvc.perform(
                put("/api/v1/timetable/"+timeTableId)
                        .header("user_uid",testUtils.getTestUid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timeTableUpdateRequestServiceDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("timetable-update-single",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("?????? timetable??? ??????").attributes(field("constraints", "?????? 1???, ?????? 50???")),
                                fieldWithPath("start_date_time").description("?????? timetable??? ?????? ??????"),
                                fieldWithPath("end_date_time").description("?????? timetable??? ?????? ??????").attributes(field("constraints", "?????? ????????? ?????? ???????????? ??? ????????? ??????????????????."))
                        ),
                        responseFields(
                                fieldWithPath("id").description("?????? timetable??? ID"),
                                fieldWithPath("name").description("?????? timetable??? name"),
                                fieldWithPath("start_date_time").description("?????? timetable??? ?????? date time"),
                                fieldWithPath("end_date_time").description("?????? timetable??? ?????? date time"),
                                fieldWithPath("created_by").description("?????? timetable??? ????????? ???"),
                                fieldWithPath("modify_by").description("?????? timetable??? ??????????????? ????????? ???"),
                                fieldWithPath("created_date_time").description("?????? timetable??? ????????? ??????"),
                                fieldWithPath("modified_date_time").description("?????? timetable??? ??????????????? ????????? ??????"),
                                fieldWithPath("event_id").description("?????? timetable??? ?????? event(object) ID"),
                                fieldWithPath("event_name").description("?????? timeTable??? ?????? event(object) ??? name")
                                )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testTimeTableResult.toControllerDto())));
        verify(timeTableService).update(timeTableId,timeTableUpdateRequestServiceDto.toServiceDto(),testUtils.getTestUid());
    }
}