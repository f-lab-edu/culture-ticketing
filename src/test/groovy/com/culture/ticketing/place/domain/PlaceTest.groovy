package com.culture.ticketing.place.domain

import spock.lang.Specification

class PlaceTest extends Specification {

    def "Place 생성 시 위도가 null 이면 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시 ")
                .latitude(null)
                .longitude(new BigDecimal(0))
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "정확한 장소 위도를 입력해주세요."
    }

    def "Place 생성 시 경도가 null 이면 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시 ")
                .latitude(new BigDecimal(0))
                .longitude(null)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == "정확한 장소 경도를 입력해주세요."
    }

    def "Place 생성 시 장소 주소가 null 이면 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address(null)
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(0))
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 주소를 입력해주세요."
    }

    def "Place 생성 시 장소 주소가 빈 값이면 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(0))
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 주소를 입력해주세요."
    }

    def "Place 생성 시 위도 범위가 -90 미만인 경우 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(-91))
                .longitude(new BigDecimal(0))
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 위도 범위를 벗어난 입력값입니다."
    }

    def "Place 생성 시 위도 범위가 90 초과인 경우 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(91))
                .longitude(new BigDecimal(0))
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 위도 범위를 벗어난 입력값입니다."
    }

    def "Place 생성 시 경도 범위가 -180 미만인 경우 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(-181))
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 경도 범위를 벗어난 입력값입니다."
    }

    def "Place 생성 시 경도 범위가 180 초과인 경우 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시")
                .latitude(new BigDecimal(0))
                .longitude(new BigDecimal(181))
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "장소 경도 범위를 벗어난 입력값입니다."
    }
}
