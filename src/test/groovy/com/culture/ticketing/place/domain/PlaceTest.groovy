package com.culture.ticketing.place.domain

import spock.lang.Specification

class PlaceTest extends Specification {

    def "Place_생성_시_위도가_null_이면_예외_발생"() {

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

    def "Place_생성_시_경도가_null_이면_예외_발생"() {

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

    def "Place_생성_시_장소_주소가_null_이면_예외_발생"() {

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

    def "Place_생성_시_장소 주소가_빈_값_이면_예외_발생"() {

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

    def "Place_생성_시_위도_범위가_-90_미만인_경우_예외_발생"() {

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

    def "Place_생성_시_위도_범위가_90_초과인_경우_예외_발생"() {

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

    def "Place_생성_시_경도_범위가_-180_미만인_경우_예외_발생"() {

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

    def "Place_생성_시_경도_범위가_180_초과인_경우_예외_발생"() {

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
