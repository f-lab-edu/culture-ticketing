package com.culture.ticketing.place.application

import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest
import com.culture.ticketing.place.exception.PlaceNotFoundException
import com.culture.ticketing.place.infra.AreaRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class AreaServiceTest extends Specification {

    @SpringBean
    private AreaRepository areaRepository = Mock();
    @SpringBean
    private PlaceService placeService = Mock();
    private AreaService areaService = new AreaService(areaRepository, placeService);

    def "구역_생성_시_장소_아이디가_null_인_경우_예외_발생"() {

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

    def "구역_생성_시_장소_아이디가_존재하지_않는_경우_예외_발생"() {

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

    def "구역_아이디_값으로_구역_존재_여부_확인"() {

        given:
        Long areaId = 1000L;
        areaRepository.existsById(areaId) >> true;

        when:
        boolean response = areaService.notExistsById(areaId);

        then:
        !response
    }
}
