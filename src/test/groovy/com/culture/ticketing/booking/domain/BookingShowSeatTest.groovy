package com.culture.ticketing.booking.domain

import spock.lang.Specification

class BookingShowSeatTest extends Specification {

    def "예약 공연 좌석 생성 시 요청 값이 null 인 경우 예외 발생"() {

        when:
        BookingShowSeat.builder()
                .bookingId(bookingId)
                .showSeatId(showSeatId)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        bookingId | showSeatId || expected
        null      | 1L         || "예약 번호를 입력해주세요."
        1L        | null       || "공연 좌석 아이디를 입력해주세요."
    }
}
