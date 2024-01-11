package com.culture.ticketing.booking.domain

import spock.lang.Specification

class BookingTest extends Specification {

    def "예약_생성_시_유저_아이디가_null_인_경우_예외_발생"() {

        when:
        Booking.builder()
                .userId(null)
                .roundId(1L)
                .totalPrice(new BigDecimal(100000))
                .bookingStatus(BookingStatus.SUCCESS)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "유저 아이디를 입력해주세요."
    }

    def "예약_생성_시_회차_아이디가_null_인_경우_예외_발생"() {

        when:
        Booking.builder()
                .userId(1L)
                .roundId(null)
                .totalPrice(new BigDecimal(100000))
                .bookingStatus(BookingStatus.SUCCESS)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 아이디를 입력해주세요."
    }

    def "예약_생성_시_예약_상태가_null_인_경우_예외_발생"() {

        when:
        Booking.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(new BigDecimal(100000))
                .bookingStatus(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "예약 상태를 입력해주세요."
    }

    def "예약_생성_시_총_가격이_0미만_인_경우_예외_발생"() {

        when:
        Booking.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(new BigDecimal(-1))
                .bookingStatus(BookingStatus.SUCCESS)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "총 가격은 0 이상 숫자로 입력해주세요."
    }
}
