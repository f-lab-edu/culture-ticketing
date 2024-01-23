package com.culture.ticketing.show.domain

import spock.lang.Specification

import java.time.LocalDate

class ShowTest extends Specification {

    def "공연 생성 시 카테고리가 null 인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(null)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 카테고리를 입력해주세요."
    }

    def "공연 생성 시 관람 제한가가 null 인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(null)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 관람 제한가를 입력해주세요."
    }

    def "공연 생성 시 장소 아이디 값이 null 인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 장소 아이디를 입력해주세요."
    }

    def "공연 생성 시 공연 시작 날짜가 null 인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(null)
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 시작 날짜를 입력해주세요."
    }

    def "공연 생성 시 공연 종료 날짜가 null 인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(null)
                .placeId(1L)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 종료 날짜를 입력해주세요."
    }

    def "공연 생성 시 공연 이름이 null 인 경우 예외 발생"() {

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
    }

    def "공연 생성 시 공연 이름이 빈 값인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("")
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
    }

    def "공연 생성 시 포스터 이미지 url 이 null 인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl(null)
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 포스터 이미지 url을 입력해주세요."
    }

    def "공연 생성 시 포스터 이미지 url 이 빈 값인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 포스터 이미지 url을 입력해주세요."
    }

    def "공연 생성 시 러닝 시간이 0이하인 경우 예외 발생"() {

        when:
        Show.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(0)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 러닝 시간을 0 초과로 입력해주세요."
    }
}
