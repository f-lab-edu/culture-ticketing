package com.culture.ticketing.show.domain

import spock.lang.Specification

class RoundPerformerTest extends Specification {

    def "회차 출연자 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        RoundPerformer.builder()
                .roundId(roundId)
                .performerId(performerId)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        roundId | performerId || expected
        null    | 1L          || "회차 아이디를 입력해주세요."
        1L      | null        || "출연자 아이디를 입력해주세요."
    }
}
