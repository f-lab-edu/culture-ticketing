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

    def "공연_좌석_정보_생성_성공"() {

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

    def "공연_좌석_정보_생성_시_공연_좌석_등급_아이디_값이_null_인_경우_400_에러"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(null)
                .seatIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연_좌석_정보_생성_시_좌석_아이디_목록_값이_null_인_경우_400_에러"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(1L)
                .seatIds(null)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }
}
