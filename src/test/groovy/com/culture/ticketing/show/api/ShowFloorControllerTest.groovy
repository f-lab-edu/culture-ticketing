package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.ShowFloorService
import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShowFloorController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowFloorControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowFloorService showFloorService = Mock();

    def "공연_플로어_생성_성공"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("F1")
                .count(700)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연_플로어_생성_시_공연_좌석_등급_아이디_값이_null_인_경우_400_에러"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(null)
                .showFloorName("F1")
                .count(700)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연_플로어_생성_시_공연_플로어_구역명이_null_인_경우_400_에러"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName(null)
                .count(700)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연_플로어_생성_시_공연_플로어_구역명이_빈_값인_경우_400_에러"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("")
                .count(700)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연_플로어_생성_시_인원수가_0이하_인_경우_400_에러"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("F1")
                .count(0)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }
}