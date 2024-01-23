package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowSeatTest extends Specification {

    def "공연 좌석 정보 생성 시 공연 좌석 등급 아이디 값이 null 인 경우 예외 발생"() {

        when:
        ShowSeat.builder()
                .showSeatGradeId(null)
                .seatId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 등급 아이디를 입력해주세요."
    }

    def "공연 좌석 정보 생성 시 좌석 아이디 값이 null 인 경우 예외 발생"() {

        when:
        ShowSeat.builder()
                .showSeatGradeId(1L)
                .seatId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "좌석 아이디를 입력해주세요."
    }
}
