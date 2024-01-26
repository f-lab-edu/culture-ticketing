package com.culture.ticketing.booking.domain

import spock.lang.Specification

class BookingShowSeatTest extends Specification {

    def "예약_공연_좌석_생성_시_예약_번호_null_인_경우_예외_발생"() {

        when:
        BookingShowSeat.builder()
                .bookingId(null)
                .showSeatId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "예약 번호를 입력해주세요."
    }

    def "예약_공연_좌석_생성_시_공연_좌석_아이디가_null_인_경우_예외_발생"() {

        when:
        BookingShowSeat.builder()
                .bookingId(1L)
                .showSeatId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 아이디를 입력해주세요."
    }
}
