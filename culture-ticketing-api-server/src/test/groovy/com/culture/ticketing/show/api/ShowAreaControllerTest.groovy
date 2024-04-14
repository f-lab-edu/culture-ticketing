package com.culture.ticketing.show.api

import com.culture.ticketing.common.config.SecurityConfig
import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.ShowAreaService
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaResponse
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest

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

@WebMvcTest(ShowAreaController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowAreaControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowAreaService showAreaService = Mock();

    @WithMockUser(roles = "ADMIN")
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

    @WithMockUser(roles = "USER")
    def "공연 아이디로 장소 구역 목록 조회"() {

        given:
        List<ShowAreaGradeResponse> showAreaGrades = [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)),
        ]

        showAreaService.findShowAreasByShowId(1L) >> [
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L), showAreaGrades.get(0)),
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L), showAreaGrades.get(0)),
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L), showAreaGrades.get(1)),
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show-areas")
                .param("showId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].showAreaId").value(1L))
                .andExpect(jsonPath("\$[0].showAreaGradeId").value(1L))
                .andExpect(jsonPath("\$[1].showAreaId").value(2L))
                .andExpect(jsonPath("\$[1].showAreaGradeId").value(1L))
                .andExpect(jsonPath("\$[2].showAreaId").value(3L))
                .andExpect(jsonPath("\$[2].showAreaGradeId").value(2L))
                .andDo(MockMvcResultHandlers.print())
    }
}
