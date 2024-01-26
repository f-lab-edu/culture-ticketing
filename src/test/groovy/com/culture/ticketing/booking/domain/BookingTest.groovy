package com.culture.ticketing.booking.domain

import spock.lang.Specification

class BookingTest extends Specification {

    def "예약 생성 시 요청 값이 null 인 경우 예외 발생"() {

        when:
        Booking.builder()
                .userId(userId)
                .roundId(roundId)
                .totalPrice(100000)
                .bookingStatus(bookingStatus)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        userId | roundId | bookingStatus         || expected
        null   | 1L      | BookingStatus.SUCCESS || "유저 아이디를 입력해주세요."
        1L     | null    | BookingStatus.SUCCESS || "회차 아이디를 입력해주세요."
        1L     | 1L      | null                  || "예약 상태를 입력해주세요."
    }

    def "예약 생성 시 요청 값이 적절하지 않은 경우 예외 발생"() {

        when:
        Booking.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(-1)
                .bookingStatus(BookingStatus.SUCCESS)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "총 가격은 0 이상 숫자로 입력해주세요."
    }
}
