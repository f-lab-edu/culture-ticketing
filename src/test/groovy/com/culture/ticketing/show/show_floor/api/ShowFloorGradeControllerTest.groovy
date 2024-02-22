package com.culture.ticketing.show.show_floor.api

import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeSaveRequest
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

@WebMvcTest(ShowFloorGradeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowFloorGradeControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowFloorGradeService showFloorGradeService = Mock();

    def "공연 플로어 등급 생성 성공"() {

        given:
        ShowFloorGradeSaveRequest request = ShowFloorGradeSaveRequest.builder()
                .showFloorGradeName("VIP")
                .price(200000)
                .showId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-floor-grades")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }
}
