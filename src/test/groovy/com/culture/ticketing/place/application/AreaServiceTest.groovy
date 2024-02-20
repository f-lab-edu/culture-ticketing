package com.culture.ticketing.place.application

import com.culture.ticketing.place.AreaFixtures
import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest
import com.culture.ticketing.place.domain.Area
import com.culture.ticketing.place.exception.PlaceNotFoundException
import com.culture.ticketing.place.infra.AreaRepository
import spock.lang.Specification

class AreaServiceTest extends Specification {

    private AreaRepository areaRepository = Mock();
    private PlaceService placeService = Mock();
    private AreaService areaService = new AreaService(areaRepository, placeService);

    def "구역 생성 성공"() {

        given:
        PlaceAreaSaveRequest request = PlaceAreaSaveRequest.builder()
                .areaName("테스트")
                .placeId(1L)
                .build();
        placeService.notExistsById(1L) >> false

        when:
        areaService.createPlaceArea(request);

        then:
        1 * areaRepository.save(_) >> { args ->

            def savedArea = args.get(0) as Area

            savedArea.areaName == "테스트"
            savedArea.placeId == 1L
        }
    }

    def "구역 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        PlaceAreaSaveRequest request = PlaceAreaSaveRequest.builder()
                .areaName("테스트")
                .placeId(null)
                .build();

        when:
        areaService.createPlaceArea(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "장소 아이디를 입력해주세요."
    }

    def "구역 생성 시 장소 아이디가 존재하지 않는 경우 예외 발생"() {

        given:
        Long placeId = 1000L;
        PlaceAreaSaveRequest request = PlaceAreaSaveRequest.builder()
                .areaName("테스트")
                .placeId(placeId)
                .build();
        placeService.notExistsById(placeId) >> true

        when:
        areaService.createPlaceArea(request);

        then:
        def e = thrown(PlaceNotFoundException.class)
        e.message == String.format("존재하지 않는 장소입니다. (placeId = %d)", placeId)
    }

    def "구역 아이디 값으로 구역 존재 여부 확인"() {

        given:
        Long areaId = 1000L;
        areaRepository.existsById(areaId) >> true;

        when:
        boolean response = areaService.notExistsById(areaId);

        then:
        !response
    }

    def "공연 아이디로 구역 목록 조회"() {

        given:
        areaRepository.findByShowId(1L) >> [
                AreaFixtures.createArea(areaId: 1L),
                AreaFixtures.createArea(areaId: 2L),
                AreaFixtures.createArea(areaId: 3L),
        ]

        when:
        List<Area> response = areaService.findByShowId(1L);

        then:
        response.size() == 3
        response.areaId == [1L, 2L, 3L]
    }
}
