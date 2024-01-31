package com.culture.ticketing.place.domain

import spock.lang.Specification

class AreaTest extends Specification {

    def "구역 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

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
