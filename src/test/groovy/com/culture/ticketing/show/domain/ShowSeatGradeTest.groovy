package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowSeatGradeTest extends Specification {

    def "공연 좌석 등급 생성 시 공연 아이디 값이 null 인 경우 예외 발생"() {

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

    def "공연 좌석 등급 생성 시 좌석 등급 명이 null 인 경우 예외 발생"() {

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

    def "공연 좌석 등급 생성 시 좌석 등급명이 빈 값인 경우 예외 발생"() {

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

    def "공연 좌석 등급 생성 시 가격이 0 미만인 경우 예외 발생"() {

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
