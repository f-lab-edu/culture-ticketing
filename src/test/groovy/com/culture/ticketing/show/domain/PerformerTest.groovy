package com.culture.ticketing.show.domain

import spock.lang.Specification

class PerformerTest extends Specification {

    def "출연자_생성_시_공연_아이디가_null_인_경우_예외_발생"() {

        when:
        Performer.builder()
                .performerName("홍길동")
                .showId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "출연자_생성_시_출연자_이름이_null_인_경우_예외_발생"() {

        when:
        Performer.builder()
                .performerName(null)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "출연자 이름을 입력해주세요."
    }

    def "출연자_생성_시_출연자_이름이_빈_값인_경우_예외_발생"() {

        when:
        Performer.builder()
                .performerName("")
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "출연자 이름을 입력해주세요."
    }
}
