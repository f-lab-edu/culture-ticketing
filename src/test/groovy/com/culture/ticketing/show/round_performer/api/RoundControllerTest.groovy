package com.culture.ticketing.show.round_performer.api

import com.culture.ticketing.common.config.SecurityConfig
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.application.RoundService
import com.culture.ticketing.show.application.ShowFacadeService
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundSaveRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RoundController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
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

    @WithMockUser(roles = "ADMIN")
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
        roundService.findByShowId(1L) >> [
                new RoundResponse(RoundFixtures.createRound(roundId: 1L, showId: 1L)),
                new RoundResponse(RoundFixtures.createRound(roundId: 2L, showId: 1L)),
                new RoundResponse(RoundFixtures.createRound(roundId: 3L, showId: 1L))
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

    @WithMockUser(roles = "USER")
    def "공연 아이디와 날짜로 회차, 출연자, 좌석 정보 조회 성공"() {

        given:
        showFacadeService.findRoundsByShowIdAndRoundStartDate(1L, LocalDate.of(2024, 1, 1)) >> [
                RoundFixtures.createRoundWithPerformersAndShowSeatsResponse(roundId: 1L, performerIds: [1L, 2L], showAreaGradeIds: [1L, 2L]),
                RoundFixtures.createRoundWithPerformersAndShowSeatsResponse(roundId: 2L, performerIds: [1L, 2L], showAreaGradeIds: [1L, 2L])
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rounds/detail")
                .param("showId", "1")
                .param("roundStartDate", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$[0].roundId").value(1))
                .andExpect(jsonPath("\$[0].performers", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$[0].showSeatCounts", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$[1].roundId").value(2))
                .andExpect(jsonPath("\$[1].performers", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$[1].showSeatCounts", Matchers.hasSize(2)))
                .andDo(MockMvcResultHandlers.print())
    }
}
