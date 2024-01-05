package com.culture.ticketing.show.domain

import spock.lang.Specification

import java.time.LocalDate

class ShowTest extends Specification {

    def "공연_생성_시_카테고리가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_관람_제한가가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_장소_아이디_값이_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_시작_날짜가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_종료_날짜가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_이름이_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_이름이_빈_값인_경우_예외_발생"() {

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

    def "공연_생성_시_포스터_이미지_url_이_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_포스터_이미지_url_이_빈_값인_경우_예외_발생"() {

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

    def "공연_생성_시_러닝_시간이_0이하_인_경우_예외_발생"() {

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
