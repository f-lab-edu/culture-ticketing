package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.ShowSeatGradeService
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest
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

@WebMvcTest(ShowSeatGradeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowSeatGradeControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowSeatGradeService showSeatGradeService = Mock();

    def "공연 좌석 등급 생성"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade("VIP")
                .price(100000)
                .showId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seat-grades")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 좌석 등급 생성 시 적절하지 않은 요청값인 경우 400 에러"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade(seatGrade)
                .price(price)
                .showId(showId)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seat-grades")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())

        where:
        seatGrade | price | showId
        "VIP" | 100000 | null
        null | 100000 | 1L
        "" | 100000 | 1L
        "VIP" | -1 | 1L
    }
}
