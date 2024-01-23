package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.ShowSeatService
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest
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

@WebMvcTest(ShowSeatController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowSeatControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowSeatService showSeatService = Mock();

    def "공연 좌석 정보 생성 성공"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(1L)
                .seatIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 좌석 정보 생성 시 적절하지 않은 요청값인 경우 400 에러"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatIds(seatIds)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())

        where:
        showSeatGradeId | seatIds
        null | Set.of(1L, 2L, 3L, 4L, 5L)
        1L | null
    }
}
