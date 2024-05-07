package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowAreaTest extends Specification {

    def "구역 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        ShowArea.builder()
                .showAreaName("테스트")
                .showId(showId)
                .showAreaGradeId(showAreaGradeId)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        showId | showAreaGradeId || expected
        null   | 1L              || "공연 아이디를 입력해주세요."
        1L     | null            || "공연 구역 등급 아이디를 입력해주세요."
    }

    def "구역 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        ShowArea.builder()
                .showAreaName(showAreaName)
                .showId(1L)
                .showAreaGradeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showAreaName || expected
        ""           || "공연 구역명을 입력해주세요."
        null         || "공연 구역명을 입력해주세요."
    }
}
