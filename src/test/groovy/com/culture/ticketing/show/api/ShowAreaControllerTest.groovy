package com.culture.ticketing.show.api

import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.ShowAreaService
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest
import com.culture.ticketing.show.application.dto.ShowAreasResponse
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShowAreaController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowAreaControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowAreaService showAreaService = Mock();

    def "장소 구역 생성 성공"() {

        given:
        ShowAreaSaveRequest request = ShowAreaSaveRequest.builder()
                .showAreaName("테스트")
                .showId(1L)
                .showAreaGradeId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-areas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 아이디로 장소 구역 목록 조회"() {

        given:
        ShowAreaGradesResponse showAreaGrades = new ShowAreaGradesResponse([
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L),
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L),
        ])

        showAreaService.findShowAreasByShowId(1L) >> new ShowAreasResponse([
                ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L),
                ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L),
                ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L),
        ], showAreaGrades)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show-areas")
                .param("showId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.showAreas").isArray())
                .andExpect(jsonPath("\$.showAreas", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$.showAreas[0].showAreaId").value(1L))
                .andExpect(jsonPath("\$.showAreas[0].showAreaGradeId").value(1L))
                .andExpect(jsonPath("\$.showAreas[1].showAreaId").value(2L))
                .andExpect(jsonPath("\$.showAreas[1].showAreaGradeId").value(1L))
                .andExpect(jsonPath("\$.showAreas[2].showAreaId").value(3L))
                .andExpect(jsonPath("\$.showAreas[2].showAreaGradeId").value(2L))
                .andDo(MockMvcResultHandlers.print())
    }
}
