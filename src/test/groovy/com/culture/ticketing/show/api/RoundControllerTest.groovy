package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.RoundService
import com.culture.ticketing.show.application.dto.RoundSaveRequest
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

import java.time.LocalDateTime

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

    def "회차 생성 시 적절하지 않은 요청값인 경우 400 에러"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(showId)
                .roundStartDateTime(roundStartDateTime)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rounds")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())

        where:
        showId | roundStartDateTime
        null | LocalDateTime.of(2024, 1, 1, 10, 0, 0)
        1L | null
    }

}
