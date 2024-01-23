package com.culture.ticketing.show.domain

import spock.lang.Specification

class RoundPerformerTest extends Specification {

    def "회차 출연자 생성 시 회차 아이디가 null 인 경우 예외 발생"() {

        when:
        RoundPerformer.builder()
                .roundId(null)
                .performerId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 아이디를 입력해주세요."
    }

    def "회차 출연자 생성 시 출연자 아이디가 null 인 경우 예외 발생"() {

        when:
        RoundPerformer.builder()
                .roundId(1L)
                .performerId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "출연자 아이디를 입력해주세요."
    }
}
