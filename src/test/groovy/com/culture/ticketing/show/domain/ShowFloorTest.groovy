package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowFloorTest extends Specification {

    def "공연 플로어 생성 시 공연 좌석 등급 아이디 값이 null 인 경우 예외 발생"() {

        when:
        ShowFloor.builder()
                .showFloorName("F1")
                .count(700)
                .showSeatGradeId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 등급 아이디를 입력해주세요."
    }

    def "공연 플로어 생성 시 공연 플로어 구역명이 null 인 경우 예외 발생"() {

        when:
        ShowFloor.builder()
                .showFloorName(null)
                .count(700)
                .showSeatGradeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 구역명을 입력해주세요."
    }

    def "공연 플로어 생성 시 공연 플로어 구역명이 빈 값인 경우 예외 발생"() {

        when:
        ShowFloor.builder()
                .showFloorName("")
                .count(700)
                .showSeatGradeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 구역명을 입력해주세요."
    }

    def "공연 플로어 생성 시 인원수가 0 이하인 경우 예외 발생"() {

        when:
        ShowFloor.builder()
                .showFloorName("F1")
                .count(0)
                .showSeatGradeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 인원수를 1 이상 숫자로 입력해주세요."
    }
}
