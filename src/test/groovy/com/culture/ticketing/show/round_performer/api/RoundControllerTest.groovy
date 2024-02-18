package com.culture.ticketing.show.round_performer.api

import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.api.RoundController
import com.culture.ticketing.show.round_performer.application.RoundService
import com.culture.ticketing.show.application.ShowFacadeService
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundSaveRequest
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

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RoundController.class)
@MockBean(JpaMetamodelMappingContext.class)
class RoundControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private RoundService roundService = Mock();
    @SpringBean
    private ShowFacadeService showFacadeService = Mock();

    def "회차 생성 성공"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rounds")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연별 회차 목록 조회 성공"() {

        given:
        roundService.findRoundResponsesByShowId(1L) >> [
                RoundResponse.from(RoundFixtures.createRound(roundId: 1L, showId: 1L)),
                RoundResponse.from(RoundFixtures.createRound(roundId: 2L, showId: 1L)),
                RoundResponse.from(RoundFixtures.createRound(roundId: 3L, showId: 1L))
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rounds")
                .param("showId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].roundId").value(1))
                .andExpect(jsonPath("\$[1].roundId").value(2))
                .andExpect(jsonPath("\$[2].roundId").value(3))
                .andDo(MockMvcResultHandlers.print())
    }
}
