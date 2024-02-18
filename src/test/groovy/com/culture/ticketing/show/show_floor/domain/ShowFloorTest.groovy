package com.culture.ticketing.show.show_floor.domain

import spock.lang.Specification

class ShowFloorTest extends Specification {

    def "공연 플로어 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        ShowFloor.builder()
                .showFloorName("F1")
                .count(700)
                .showFloorGradeId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 플로어 등급 아이디를 입력해주세요."
    }

    def "공연 플로어 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        ShowFloor.builder()
                .showFloorName(null)
                .count(700)
                .showFloorGradeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 구역명을 입력해주세요."

        where:
        showFloorName | count || expected
        null          | 700   || "공연 플로어 구역명을 입력해주세요."
        ""            | 700   || "공연 플로어 구역명을 입력해주세요."
        "F1"          | 0     || "공연 플로어 인원수를 1 이상 숫자로 입력해주세요."
    }
}
