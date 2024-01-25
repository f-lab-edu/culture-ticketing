package com.culture.ticketing.place.domain

import spock.lang.Specification

class PlaceTest extends Specification {

    def "Place 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        when:
        Place.builder()
                .placeName("테스트")
                .address("서울특별시 ")
                .latitude(latitude)
                .longitude(longitude)
                .build();

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        latitude | longitude || expected
        null     | 102.6     || "정확한 장소 위도를 입력해주세요."
        36.1     | null      || "정확한 장소 경도를 입력해주세요."
    }

    def "Place 생성 시 요청 값에 적절하지 않은 값 들어간 경우 예외 발생"() {

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

        where:
        address | latitude | longitude || expected
        null    | 36.1     | 102.6     || "장소 주소를 입력해주세요."
        ""      | 36.1     | 102.6     || "장소 주소를 입력해주세요."
        "서울특별시" | -91      | 102.6     || "장소 위도 범위를 벗어난 입력값입니다."
        "서울특별시" | 91       | 102.6     || "장소 위도 범위를 벗어난 입력값입니다."
        "서울특별시" | 36.1     | -181      || "장소 경도 범위를 벗어난 입력값입니다."
        "서울특별시" | 36.1     | 181       || "장소 경도 범위를 벗어난 입력값입니다."
    }

}
