package com.culture.ticketing.show.domain

import spock.lang.Specification

class PerformerTest extends Specification {

    def "출연자 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        Performer.builder()
                .performerName("홍길동")
                .showId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "출연자 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        Performer.builder()
                .performerName(performerName)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        performerName || expected
        null          || "출연자 이름을 입력해주세요."
        ""            || "출연자 이름을 입력해주세요."
    }
}
