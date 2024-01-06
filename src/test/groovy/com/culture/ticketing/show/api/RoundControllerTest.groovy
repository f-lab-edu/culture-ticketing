package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.RoundService
import com.culture.ticketing.show.application.ShowFacadeService
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

    def "회차_생성_성공"() {

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

    def "회차_생성_시_공연_아이디가_null_인_경우_400_에러"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(null)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rounds")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "회차_생성_시_회차_시작_일시가_null_인_경우_400_에러"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(null)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rounds")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }
}
