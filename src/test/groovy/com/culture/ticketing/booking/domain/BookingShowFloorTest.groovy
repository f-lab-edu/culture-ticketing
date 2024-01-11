package com.culture.ticketing.booking.domain

import spock.lang.Specification

class BookingShowFloorTest extends Specification {

    def "예약_공연_플로어_정보_생성_시_예약_번호가_null_인_경우_예외_발생"() {

        when:
        BookingShowFloor.builder()
                .bookingId(null)
                .showFloorId(1L)
                .entryOrder(100)
                .build();

        then:
        def e = thrown(NullPointerException.class);
        e.message == "예약 번호를 입력해주세요."
    }

    def "예약_공연_플로어_정보_생성_시_공연_플로어_아이디가_null_인_경우_예외_발생"() {

        when:
        BookingShowFloor.builder()
                .bookingId(1L)
                .showFloorId(null)
                .entryOrder(100)
                .build();

        then:
        def e = thrown(NullPointerException.class);
        e.message == "공연 플로어 아이디를 입력새주세요."
    }

    def "예약_공연_플로어_정보_생성_시_공연_플로어_입장번호가_0이하_인_경우_예외_발생"() {

        when:
        BookingShowFloor.builder()
                .bookingId(1L)
                .showFloorId(1L)
                .entryOrder(0)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class);
        e.message == "플로어 입장 번호는 1 이상 숫자로 입력해주세요."
    }
}
