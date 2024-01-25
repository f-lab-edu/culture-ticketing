package com.culture.ticketing.show.domain

import spock.lang.Specification

import java.time.LocalDate

class ShowTest extends Specification {

    def "공연 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        Show.builder()
                .category(category)
                .showName("테스트")
                .ageRestriction(ageRestriction)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(showStartDate)
                .showEndDate(showEndDate)
                .placeId(placeId)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        category         | ageRestriction     | placeId | showStartDate            | showEndDate               || expected
        null             | AgeRestriction.ALL | 1L      | LocalDate.of(2024, 1, 1) | LocalDate.of(2024, 5, 31) || "공연 카테고리를 입력해주세요."
        Category.CONCERT | null               | 1L      | LocalDate.of(2024, 1, 1) | LocalDate.of(2024, 5, 31) || "공연 관람 제한가를 입력해주세요."
        Category.CONCERT | AgeRestriction.ALL | null    | LocalDate.of(2024, 1, 1) | LocalDate.of(2024, 5, 31) || "공연 장소 아이디를 입력해주세요."
        Category.CONCERT | AgeRestriction.ALL | 1L      | null                     | LocalDate.of(2024, 5, 31) || "공연 시작 날짜를 입력해주세요."
        Category.CONCERT | AgeRestriction.ALL | 1L      | LocalDate.of(2024, 1, 1) | null                      || "공연 종료 날짜를 입력해주세요."
    }

    def "공연 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName(null)
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 이름을 입력해주세요."

        where:
        showName | runningTime | posterImgUrl     || expected
        null     | 120         | "http://abc.jpg" || "공연 이름을 입력해주세요."
        ""       | 120         | "http://abc.jpg" || "공연 이름을 입력해주세요."
        "테스트"    | 120         | null             || "공연 포스터 이미지 url을 입력해주세요."
        "테스트"    | 120         | ""               || "공연 포스터 이미지 url을 입력해주세요."
        "테스트"    | 0           | "http://abc.jpg" || "공연 러닝 시간을 0 초과로 입력해주세요."
    }
}
