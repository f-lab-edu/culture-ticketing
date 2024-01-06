package com.culture.ticketing.place.application

import com.culture.ticketing.place.PlaceFixtures
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
        List<Place> places = List.of(
                PlaceFixtures.createPlace(1L),
                PlaceFixtures.createPlace(2L),
                PlaceFixtures.createPlace(3L),
                PlaceFixtures.createPlace(4L),
                PlaceFixtures.createPlace(5L)
        );
        placeRepository.findByPlaceIdGreaterThanLimit(1L, 3) >> places.subList(1, 4)

        when:
        List<PlaceResponse> response = placeService.findPlaces(1L, 3);

        then:
        response.collect(place -> place.placeId > 1L).size() == 3
    }
}
