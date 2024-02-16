package com.culture.ticketing.booking.domain

import com.culture.ticketing.booking.BookingFixtures
import spock.lang.Specification

class BookingShowFloorTest extends Specification {

    def "예약 공연 플로어 정보 생성 시 요청 값이 null 인 경우 예외 발생"() {

        when:
        BookingShowFloor.builder()
                .booking(booking)
                .showFloorId(showFloorId)
                .entryOrder(100)
                .build();

        then:
        def e = thrown(NullPointerException.class);
        e.message == expected

        where:
        booking                                      | showFloorId || expected
        null                                         | 1L          || "예약 정보를 입력해주세요."
        BookingFixtures.createBooking(bookingId: 1L) | null        || "공연 플로어 아이디를 입력새주세요."
    }

    def "예약 공연 플로어 정보 생성 시 요청 값이 적절하지 않은 경우 예외 발생"() {

        when:
        BookingShowFloor.builder()
                .booking(BookingFixtures.createBooking(bookingId: 1L))
                .showFloorId(1L)
                .entryOrder(0)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class);
        e.message == "플로어 입장 번호는 1 이상 숫자로 입력해주세요."
    }
}
