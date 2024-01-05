package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowSeatGradeTest extends Specification {

    def "공연_좌석_등급_생성_시_공연_아이디_값이_null_인_경우_예외_발생"() {

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

    def "공연_좌석_등급_생성_시_좌석_등급_명이_null_인_경우_예외_발생"() {

        when:
        ShowSeatGrade.builder()
                .seatGrade(null)
                .price(100000)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 좌석 등급을 입력해주세요."
    }

    def "공연_좌석_등급_생성_시_좌석_등급_명이_빈_값_인_경우_예외_발생"() {

        when:
        ShowSeatGrade.builder()
                .seatGrade("")
                .price(100000)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 좌석 등급을 입력해주세요."
    }

    def "공연_좌석_등급_생성_시_가격이_0미만_인_경우_예외_발생"() {

        when:
        ShowSeatGrade.builder()
                .seatGrade("VIP")
                .price(-1)
                .showId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 좌석 가격을 0 이상으로 입력해주세요."
    }
}
