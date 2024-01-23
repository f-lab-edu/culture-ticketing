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

    def "공연 플로어 생성 성공"() {

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

    def "공연 플로어 생성 시 적절하지 않은 요청값인 경우 400 에러"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .showFloorName(showFloorName)
                .count(count)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())

        where:
        showSeatGradeId | showFloorName | count
        null | "F1" | 700
        1L | null | 700
        1L | "" | 700
        1L | "F1" | 0
    }
}
