package com.culture.ticketing.show.domain

import spock.lang.Specification

class RoundPerformerTest extends Specification {

    def "회차_출연자_생성_시_회차_아이디가_null_인_경우_예외_발생"() {

        when:
        RoundPerformer.builder()
                .roundId(null)
                .performerId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 아이디를 입력해주세요."
    }

    def "회차_출연자_생성_시_출연자_아이디가_null_인_경우_예외_발생"() {

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
