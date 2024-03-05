package com.culture.ticketing.show.infra

import com.culture.ticketing.show.PlaceFixtures
import com.culture.ticketing.show.domain.Place
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceRepositoryCustomTest extends Specification {

    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    void setup() {
        placeRepository.deleteAll();
    }

    def "장소 목록 조회 테스트 - 특정한 아이디보다 크고 사이즈 제한"() {

        given:
        placeRepository.saveAll([
                PlaceFixtures.createPlace(placeId: 1L),
                PlaceFixtures.createPlace(placeId: 2L),
                PlaceFixtures.createPlace(placeId: 3L),
                PlaceFixtures.createPlace(placeId: 4L),
                PlaceFixtures.createPlace(placeId: 5L)
        ]);

        when:
        List<Place> foundPlaces = placeRepository.findByPlaceIdGreaterThanLimit(1L, 3);

        then:
        foundPlaces.collect(place -> place.placeId > 1L).size() == 3
    }
}
