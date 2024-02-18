package com.culture.ticketing.show.show_seat.domain

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade
import spock.lang.Specification

class ShowSeatGradeTest extends Specification {

    def "공연 좌석 등급 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        ShowSeatGrade.builder()
                .seatGrade("VIP")
                .price(100000)
                .showId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "공연 좌석 등급 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        ShowSeatGrade.builder()
                .seatGrade(seatGrade)
                .price(price)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        seatGrade | price  || expected
        null      | 100000 || "공연 좌석 등급을 입력해주세요."
        ""        | 100000 || "공연 좌석 등급을 입력해주세요."
        "VIP"     | -1     || "공연 좌석 가격을 0 이상으로 입력해주세요."
    }
}
