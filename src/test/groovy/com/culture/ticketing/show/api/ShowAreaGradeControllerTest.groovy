package com.culture.ticketing.show.api

import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.ShowAreaGradeService
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaGradeSaveRequest

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

@WebMvcTest(ShowAreaGradeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowAreaGradeControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowAreaGradeService showAreaGradeService = Mock();

    def "공연 구역 등급 생성"() {

        given:
        ShowAreaGradeSaveRequest request = ShowAreaGradeSaveRequest.builder()
                .showAreaGradeName("R")
                .price(100000)
                .showId(1L)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show-area-grades")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 아이디로 공연 구역 등급 목록 조회"() {

        given:
        showAreaGradeService.findShowAreaGradesByShowId(1L) >> [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 3L)),
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show-area-grades")
                .param("showId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").exists())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].showAreaGradeId").value(1L))
                .andExpect(jsonPath("\$[1].showAreaGradeId").value(2L))
                .andExpect(jsonPath("\$[2].showAreaGradeId").value(3L))
                .andDo(MockMvcResultHandlers.print())
    }
}
