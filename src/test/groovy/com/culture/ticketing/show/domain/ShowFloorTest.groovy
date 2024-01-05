package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowFloorTest extends Specification {

    def "공연_플로어_생성_시_공연_좌석_등급_아이디_값이_null_인_경우_예외_발생"() {

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

    def "공연_플로어_생성_시_공연_플로어_구역명이_null_인_경우_예외_발생"() {

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

    def "공연_플로어_생성_시_공연_플로어_구역명이_빈_값인_경우_예외_발생"() {

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

    def "공연_플로어_생성_시_인원수가_0이하_인_경우_예외_발생"() {

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
