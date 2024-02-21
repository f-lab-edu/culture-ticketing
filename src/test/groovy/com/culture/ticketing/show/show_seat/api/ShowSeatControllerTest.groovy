package com.culture.ticketing.show.show_seat.api

import com.culture.ticketing.place.SeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.show_seat.application.ShowSeatFacadeService
import com.culture.ticketing.show.show_seat.application.ShowSeatService
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatResponse
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatSaveRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
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
    @SpringBean
    private ShowSeatFacadeService showSeatFacadeService = Mock();

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

    def "회차 아이디와 구역 아이디로 공연 좌석 목록 조회"() {

        given:
        showSeatFacadeService.findByRoundIdAndAreaId(1L, 1L) >> [
                new ShowSeatResponse(
                        ShowSeatFixtures.createShowSeat(showSeatId: 1L, seatId: 1L, showSeatGradeId: 1L),
                        SeatFixtures.creatSeat(seatId: 1L),
                        ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L),
                        true
                ),
                new ShowSeatResponse(
                        ShowSeatFixtures.createShowSeat(showSeatId: 2L, seatId: 2L, showSeatGradeId: 1L),
                        SeatFixtures.creatSeat(seatId: 2L),
                        ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L),
                        false
                ),
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show-seats")
                .param("roundId", "1")
                .param("areaId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").exists())
                .andExpect(jsonPath("\$", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$[0].showSeatId").value(1L))
                .andExpect(jsonPath("\$[0].seatId").value(1L))
                .andExpect(jsonPath("\$[0].showSeatGradeId").value(1L))
                .andExpect(jsonPath("\$[1].showSeatId").value(2L))
                .andExpect(jsonPath("\$[1].seatId").value(2L))
                .andExpect(jsonPath("\$[1].showSeatGradeId").value(1L))
                .andDo(MockMvcResultHandlers.print())
    }
}
