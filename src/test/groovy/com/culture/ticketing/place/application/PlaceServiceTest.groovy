package com.culture.ticketing.place.application

import com.culture.ticketing.place.application.dto.PlaceResponse
import com.culture.ticketing.place.domain.Place
import com.culture.ticketing.place.infra.PlaceRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class PlaceServiceTest extends Specification {

    @SpringBean
    private PlaceRepository placeRepository = Mock();
    private PlaceService placeService = new PlaceService(placeRepository);

    def "장소_목록_조회"() {

        given:
        List<Place> places = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Place place = Place.builder()
                    .placeId(i)
                    .placeName("장소" + i)
                    .address("서울특별시 " + i)
                    .latitude(new BigDecimal(i))
                    .longitude(new BigDecimal(i))
                    .build();
            places.add(place);
        }
        placeRepository.findByPlaceIdGreaterThanLimit(places.get(0).placeId, 3) >> places.subList(1, 4)

        when:
        List<PlaceResponse> response = placeService.findPlaces(places.get(0).placeId, 3);

        then:
        response.size() == 3
        response.get(0).placeId > places.get(0).placeId
        response.get(1).placeId > places.get(0).placeId
        response.get(2).placeId > places.get(0).placeId
    }
}
