package com.culture.ticketing.show.domain

import com.culture.ticketing.show.RoundFixtures
import spock.lang.Specification

import java.time.LocalDateTime

class RoundTest extends Specification {

    def "회차_생성_시_공연_아이디가_null_인_경우_예외_발생"() {

        when:
        Round.builder()
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .roundEndDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .showId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "회차_생성_시_회차_시작_일시_가_null_인_경우_예외_발생"() {

        when:
        Round.builder()
                .roundStartDateTime(null)
                .roundEndDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .showId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 시작 일시를 입력해주세요."
    }

    def "회차_생성_시_회차_종료_일시가_null_인_경우_예외_발생"() {

        when:
        Round.builder()
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .roundEndDateTime(null)
                .showId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 종료 일시를 입력해주세요."
    }
}
