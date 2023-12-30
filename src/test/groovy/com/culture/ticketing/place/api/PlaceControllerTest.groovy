package com.culture.ticketing.place.api

import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.place.application.dto.PlaceResponse
import com.culture.ticketing.place.domain.Place
import org.hamcrest.Matchers
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(PlaceController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PlaceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @SpringBean
    private PlaceService placeService = Mock()


    def "장소_목록 조회"() {

        given:
        List<PlaceResponse> places = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Place place = Place.builder()
                    .placeId(i)
                    .placeName("장소" + i)
                    .address("서울특별시 " + i)
                    .latitude(new BigDecimal(i))
                    .longitude(new BigDecimal(i))
                    .build();
            places.add(new PlaceResponse(place));
        }
        placeService.findPlaces(places.get(0).placeId, 3) >> places.subList(1, 4)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/places")
                .param("offset", places.get(0).placeId.toString())
                .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].placeId", Matchers.greaterThan(places.get(0).placeId.toInteger())))
                .andExpect(jsonPath("\$[1].placeId", Matchers.greaterThan(places.get(0).placeId.toInteger())))
                .andExpect(jsonPath("\$[2].placeId", Matchers.greaterThan(places.get(0).placeId.toInteger())))
                .andDo(MockMvcResultHandlers.print())

    }
}
