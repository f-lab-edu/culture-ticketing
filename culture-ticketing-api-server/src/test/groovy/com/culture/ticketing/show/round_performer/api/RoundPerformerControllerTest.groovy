package com.culture.ticketing.show.round_performer.api

import com.culture.ticketing.config.SecurityConfig
import com.culture.ticketing.show.round_performer.application.RoundPerformerService
import com.culture.ticketing.show.round_performer.application.dto.RoundPerformersSaveRequest
import com.fasterxml.jackson.databind.ObjectMapper
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RoundPerformerController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class RoundPerformerControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private RoundPerformerService roundPerformerService = Mock();

    @WithMockUser(roles = "ADMIN")
    def "회차 출연자 목록 생성 성공"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(1L)
                .performerIds(Set.of(1L, 2L, 3L))
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/round-performers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

}
