package com.culture.ticketing.show.api

import com.culture.ticketing.common.config.SecurityConfig
import com.culture.ticketing.show.ShowSeatFixtures
import com.culture.ticketing.show.application.ShowFacadeService
import com.culture.ticketing.show.application.ShowSeatService
import com.culture.ticketing.show.application.dto.ShowSeatResponse
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShowSeatController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowSeatControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowSeatService showSeatService = Mock();
    @SpringBean
    private ShowFacadeService showFacadeService = Mock();

    @WithMockUser(roles = "ADMIN")
    def "공연 좌석 생성 성공"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatRow("A")
                .showSeatNumber(1)
                .showAreaId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    @WithMockUser(roles = "USER")
    def "공연 구역 아이디와 회차 아이디로 공연 좌석 목록 조회"() {

        given:
        showFacadeService.findShowSeatsByShowAreaIdAndRoundId(1L, 1L) >> [
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 1L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 2L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 3L)).getAvailabilityUpdatedShowSeatResponse(false),
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show-seats")
                .param("showAreaId", "1")
                .param("roundId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].showSeatId").value(1L))
                .andExpect(jsonPath("\$[0].isAvailable").value(true))
                .andExpect(jsonPath("\$[1].showSeatId").value(2L))
                .andExpect(jsonPath("\$[1].isAvailable").value(true))
                .andExpect(jsonPath("\$[2].showSeatId").value(3L))
                .andExpect(jsonPath("\$[2].isAvailable").value(false))
                .andDo(MockMvcResultHandlers.print())
    }
}
