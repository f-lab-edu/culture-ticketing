package com.culture.ticketing.place.domain

import spock.lang.Specification

class SeatTest extends Specification {

    def "좌석 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

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

    def "좌석 생성 시 요청 값에 적잘하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        Seat.builder()
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .areaId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        seatRow | seatNumber || expected
        0       | 1          || "좌석 행을 1 이상 숫자로 입력해주세요."
        1       | 0          || "좌석 번호를 1 이상 숫자로 입력해주세요."
    }
}
