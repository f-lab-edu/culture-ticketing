package com.culture.ticketing.show.round_performer.api

import com.culture.ticketing.config.SecurityConfig
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.application.PerformerService
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.PerformerSaveRequest

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

@WebMvcTest(PerformerController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class PerformerControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private PerformerService performerService = Mock();

    @WithMockUser(roles = "ADMIN")
    def "출연자 생성 성공"() {

        given:
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(1L)
                .performerName("홍길동")
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/performers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연별 출연자 목록 조회 성공"() {

        given:
        performerService.findPerformersByShowId(1L) >> [
                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L, showId: 1L)),
                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L, showId: 1L)),
                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 4L, showId: 1L))
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/performers")
                .param("showId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].performerId").value(1))
                .andExpect(jsonPath("\$[1].performerId").value(2))
                .andExpect(jsonPath("\$[2].performerId").value(4))
                .andDo(MockMvcResultHandlers.print())
    }
}
