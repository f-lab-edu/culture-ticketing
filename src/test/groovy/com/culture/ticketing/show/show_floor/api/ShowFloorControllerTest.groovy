package com.culture.ticketing.show.show_floor.api

import com.culture.ticketing.show.show_floor.ShowFloorFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorService
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorResponse
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorSaveRequest
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShowFloorController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowFloorControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowFloorService showFloorService = Mock();

    def "공연 플로어 생성 성공"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showFloorGradeId(1L)
                .showFloorName("F1")
                .count(700)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 플로어 목록 조회"() {

        given:
        showFloorService.findByShowId(1L) >> [
                ShowFloorResponse.from(ShowFloorFixtures.createShowFloor(showFloorId: 1L)),
                ShowFloorResponse.from(ShowFloorFixtures.createShowFloor(showFloorId: 2L)),
                ShowFloorResponse.from(ShowFloorFixtures.createShowFloor(showFloorId: 3L)),
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show-floors")
                .param("showId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").exists())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].showFloorId").value(1L))
                .andExpect(jsonPath("\$[1].showFloorId").value(2L))
                .andExpect(jsonPath("\$[2].showFloorId").value(3L))
                .andDo(MockMvcResultHandlers.print())
    }
}
