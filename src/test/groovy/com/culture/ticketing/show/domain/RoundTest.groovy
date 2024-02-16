package com.culture.ticketing.show.domain

import spock.lang.Specification

import java.time.LocalDateTime

class RoundTest extends Specification {

    def "회차 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        Round.builder()
                .roundStartDateTime(roundStartDateTime)
                .roundEndDateTime(roundEndDateTime)
                .showId(showId)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        showId | roundStartDateTime                     | roundEndDateTime                       || expected
        null   | LocalDateTime.of(2024, 1, 1, 10, 0, 0) | LocalDateTime.of(2024, 1, 1, 12, 0, 0) || "공연 아이디를 입력해주세요."
        1L     | null                                   | LocalDateTime.of(2024, 1, 1, 12, 0, 0) || "회차 시작 일시를 입력해주세요."
        1L     | LocalDateTime.of(2024, 1, 1, 10, 0, 0) | null                                   || "회차 종료 일시를 입력해주세요."
    }
}
