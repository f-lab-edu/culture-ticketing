package com.culture.ticketing.place.infra

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.domain.Place
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceRepositoryCustomTest extends Specification {

    @Autowired
    private PlaceRepository placeRepository;

    def "장소 목록 조회 테스트 - 특정한 아이디보다 크고 사이즈 제한"() {

        given:
        List<Place> places = [
                PlaceFixtures.createPlace(1L),
                PlaceFixtures.createPlace(2L),
                PlaceFixtures.createPlace(3L),
                PlaceFixtures.createPlace(4L),
                PlaceFixtures.createPlace(5L)
        ];
        placeRepository.saveAll(places);

        when:
        List<Place> foundPlaces = placeRepository.findByPlaceIdGreaterThanLimit(1L, 3);

        then:
        foundPlaces.collect(place -> place.placeId > 1L).size() == 3
    }
}
