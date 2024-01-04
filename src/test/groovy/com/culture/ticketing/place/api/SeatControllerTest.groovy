package com.culture.ticketing.place.api

import com.culture.ticketing.place.application.SeatService
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest
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

@WebMvcTest(SeatController.class)
@MockBean(JpaMetamodelMappingContext.class)
class SeatControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private SeatService seatService = Mock();

    def "좌석_생성_성공"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "좌석_생성_시_구역_아이디_값이_null_인_경우_400_에러"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(null)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "좌석_생성_시_좌석_행이_0이하_인_경우_400_에러"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(0)
                .seatNumber(1)
                .areaId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "좌석_생성_시_좌석_번호가_0이하_인_경우_400_에러"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(0)
                .areaId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }
}
