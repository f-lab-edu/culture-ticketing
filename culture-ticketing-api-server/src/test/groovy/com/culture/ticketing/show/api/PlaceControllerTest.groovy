package com.culture.ticketing.show.api

import com.culture.ticketing.config.SecurityConfig
import com.culture.ticketing.show.PlaceFixtures
import com.culture.ticketing.show.application.PlaceService
import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.application.dto.PlaceSaveRequest

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(PlaceController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class PlaceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private PlaceService placeService = Mock()

    @WithMockUser(roles = "ADMIN")
    def "장소 목록 조회"() {

        given:
        Long offset = 1L
        placeService.findPlaces(offset, 3) >> [
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 2L)),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 3L)),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 4L))
        ]

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/places")
                .param("offset", offset.toString())
                .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].placeId", Matchers.greaterThan(offset.toInteger())))
                .andExpect(jsonPath("\$[1].placeId", Matchers.greaterThan(offset.toInteger())))
                .andExpect(jsonPath("\$[2].placeId", Matchers.greaterThan(offset.toInteger())))
                .andDo(MockMvcResultHandlers.print())

    }

    @WithMockUser(roles = "ADMIN")
    def "장소 생성 성공"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(0))
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())

    }

}
