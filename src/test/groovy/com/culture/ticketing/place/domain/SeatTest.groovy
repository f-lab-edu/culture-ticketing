package com.culture.ticketing.place.domain

import spock.lang.Specification

class SeatTest extends Specification {

    def "좌석_생성_시_구역_아이디가_null_인_경우_예외_발생"() {

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

    def "좌석_생성_시_좌석_행이_0이하_인_경우_예외_발생"() {

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

    def "좌석_생성_시_좌석_번호가_0이하_인_경우_예외_발생"() {

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
