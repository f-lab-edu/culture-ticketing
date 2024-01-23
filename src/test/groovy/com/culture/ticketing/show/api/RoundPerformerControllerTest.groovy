package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.RoundPerformerService
import com.culture.ticketing.show.application.dto.RoundPerformersSaveRequest
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

@WebMvcTest(RoundPerformerController.class)
@MockBean(JpaMetamodelMappingContext.class)
class RoundPerformerControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private RoundPerformerService roundPerformerService = Mock();

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

    def "회차 출연자 목록 생성 시 적절하지 않은 요청값인 경우 400 에러"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(roundId)
                .performerIds(performerIds)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/round-performers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())

        where:
        roundId | performerIds
        null | Set.of(1L, 2L, 3L)
        1L | null
    }
}
