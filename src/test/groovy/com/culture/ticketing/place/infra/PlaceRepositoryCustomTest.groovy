package com.culture.ticketing.place.infra

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

    def "장소_목록_조회_테스트_특정한_아이디보다_크고_사이즈_제한"() {

        given:
        List<Place> places = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Place place = Place.builder()
                    .placeName("장소" + i)
                    .address("서울특별시 " + i)
                    .latitude(new BigDecimal(i))
                    .longitude(new BigDecimal(i))
                    .build();
            places.add(place);
        }
        placeRepository.saveAll(places);

        when:
        List<Place> foundPlaces = placeRepository.findByPlaceIdGreaterThanLimit(places.get(0).getPlaceId(), 3);

        then:
        foundPlaces.size() == 3
        foundPlaces.get(0).placeId > places.get(0).placeId
        foundPlaces.get(1).placeId > places.get(0).placeId
        foundPlaces.get(2).placeId > places.get(0).placeId
    }
}
