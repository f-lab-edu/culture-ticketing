package com.culture.ticketing.show.show_floor.domain

import spock.lang.Specification

class ShowFloorGradeTest extends Specification {

    def "공연 플로어 등급 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        ShowFloorGrade.builder()
                .showFloorGradeName("VIP")
                .price(200000)
                .showId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "공연 플로어 등급 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        ShowFloorGrade.builder()
                .showFloorGradeName(showFloorGradeName)
                .price(price)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showFloorGradeName | price  || expected
        null               | 200000 || "공연 플로어 등급명을 입력해주세요."
        ""                 | 200000 || "공연 플로어 등급명을 입력해주세요."
        "VIP"              | -1     || "공연 플로어 가격을 0 이상으로 입력해주세요."
    }
}
