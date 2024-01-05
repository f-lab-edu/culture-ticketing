package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowSeatTest extends Specification {

    def "공연_좌석_정보_생성_시_공연_좌석_등급_아이디_값이_null_인_경우_예외_발생"() {

        when:
        ShowSeat.builder()
                .showSeatGradeId(null)
                .seatId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 등급 아이디를 입력해주세요."
    }

    def "공연_좌석_정보_생성_시_좌석_아이디_값이_null_인_경우_예외_발생"() {

        when:
        ShowSeat.builder()
                .showSeatGradeId(1L)
                .seatId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "좌석 아이디를 입력해주세요."
    }
}
