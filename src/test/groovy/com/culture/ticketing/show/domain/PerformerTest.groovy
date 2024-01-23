package com.culture.ticketing.show.domain

import spock.lang.Specification

class PerformerTest extends Specification {

    def "출연자 생성 시 공연 아이디가 null 인 경우 예외 발생"() {

        when:
        Performer.builder()
                .performerName("홍길동")
                .showId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "출연자 생성 시 출연자 이름이 null 인 경우 예외 발생"() {

        when:
        Performer.builder()
                .performerName(null)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "출연자 이름을 입력해주세요."
    }

    def "출연자 생성 시 출연자 이름이 빈 값인 경우 예외 발생"() {

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
