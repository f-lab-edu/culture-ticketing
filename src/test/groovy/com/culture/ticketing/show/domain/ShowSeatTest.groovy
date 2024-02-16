package com.culture.ticketing.show.domain

import spock.lang.Specification

class ShowSeatTest extends Specification {

    def "공연 좌석 정보 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        ShowSeat.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatId(seatId)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        showSeatGradeId | seatId || expected
        null            | 1L     || "공연 좌석 등급 아이디를 입력해주세요."
        1L              | null   || "좌석 아이디를 입력해주세요."
    }
}
