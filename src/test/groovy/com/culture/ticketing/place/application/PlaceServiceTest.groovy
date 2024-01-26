package com.culture.ticketing.place.application

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.dto.PlaceResponse
import com.culture.ticketing.place.application.dto.PlaceSaveRequest
import com.culture.ticketing.place.domain.Place
import com.culture.ticketing.place.infra.PlaceRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class PlaceServiceTest extends Specification {

    @SpringBean
    private PlaceRepository placeRepository = Mock();
    private PlaceService placeService = new PlaceService(placeRepository);

    def "장소 목록 조회"() {

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

    def "장소 생성 시 위도가 null 이면 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(null)
                .longitude(new BigDecimal(0))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "정확한 장소 위도를 입력해주세요."
    }

    def "장소 생성 시 경도가 null 이면 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(0))
                .longitude(null)
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "정확한 장소 경도를 입력해주세요."
    }

    def "장소 생성시 장소 주소가 null 이면 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address(null)
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(0))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 주소를 입력해주세요."
    }

    def "장소 생성 시 장소 주소가 빈 값 이면 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(0))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 주소를 입력해주세요."
    }

    def "장소 생성 시 위도 범위가 -90 미만인 경우 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(-91))
                .longitude(new BigDecimal(0))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 위도 범위를 벗어난 입력값입니다."
    }

    def "장소 생성 시 위도 범위가 90 초과인 경우 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(91))
                .longitude(new BigDecimal(0))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 위도 범위를 벗어난 입력값입니다."
    }

    def "장소 생성 시 경도 범위가 -180 미만인 경우 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(-181))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 경도 범위를 벗어난 입력값입니다."
    }

    def "장소 생성 시 경도 범위가 180 초과인 경우 예외 발생"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(181))
                .build();

        when:
        placeService.createPlace(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 경도 범위를 벗어난 입력값입니다."
    }

    def "장소 생성 성공"() {

        given:
        PlaceSaveRequest request = PlaceSaveRequest.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(36.1))
                .longitude(new BigDecimal(102.6))
                .build();

        when:
        placeService.createPlace(request);

        then:
        1 * placeRepository.save(_)
    }

    def "장소_아이디_값으로_장소_존재_여부_확인"() {

        given:
        Long placeId = 1000L;
        placeRepository.existsById(placeId) >> true;

        when:
        boolean response = placeService.notExistsById(placeId);

        then:
        !response
    }
}
