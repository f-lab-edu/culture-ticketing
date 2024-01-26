package com.culture.ticketing.place.domain

import spock.lang.Specification

class SeatTest extends Specification {

    def "좌석 생성 시 구역 아이디가 null 인 경우 예외 발생"() {

        when:
        Seat.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "구역 아이디를 입력해주세요."
    }

    def "좌석 생성 시 좌석 행이 0 이하 인 경우 예외 발생"() {

        when:
        Seat.builder()
                .seatRow(0)
                .seatNumber(1)
                .areaId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 행을 1 이상 숫자로 입력해주세요."
    }

    def "좌석 생성 시 좌석 번호가 0 이하 인 경우 예외 발생"() {

        when:
        Seat.builder()
                .seatRow(1)
                .seatNumber(0)
                .areaId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 번호를 1 이상 숫자로 입력해주세요."
    }
}
