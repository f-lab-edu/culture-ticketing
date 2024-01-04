package com.culture.ticketing.place.domain

import spock.lang.Specification

class AreaTest extends Specification {

    def "구역_생성_시_장소_아이디가_null_인_경우_예외_발생"() {

        when:
        Area.builder()
                .areaName("테스트")
                .placeId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "장소 아이디를 입력해주세요."
    }
}
