package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowSeatTest extends Specification {

    def "공연 좌석 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        ShowSeat.builder()
                .showSeatRow("A")
                .showSeatNumber(1)
                .showAreaId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 구역 아이디를 입력해주세요."
    }

    def "공연 좌석 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        ShowSeat.builder()
                .showSeatRow("A")
                .showSeatNumber(0)
                .showAreaId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 번호를 1 이상 숫자로 입력해주세요."
    }
}
