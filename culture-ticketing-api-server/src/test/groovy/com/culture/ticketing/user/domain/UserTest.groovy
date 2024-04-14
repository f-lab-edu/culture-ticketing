package com.culture.ticketing.user.domain

import spock.lang.Specification

class UserTest extends Specification {

    def "유저 생성 시 요청 값이 적절하지 않은 경우 예외 발생"() {

        when:
        User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .role(Role.USER)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        email            | password   | userName | phoneNumber   || expected
        null             | "password" | "테스터"    | "01000000000" || "이메일을 입력해주세요."
        ""               | "password" | "테스터"    | "01000000000" || "이메일을 입력해주세요."
        "test@naver.com" | null       | "테스터"    | "01000000000" || "비밀번호를 입력해주세요."
        "test@naver.com" | ""         | "테스터"    | "01000000000" || "비밀번호를 입력해주세요."
        "test@naver.com" | "password" | null     | "01000000000" || "이름을 입력해주세요."
        "test@naver.com" | "password" | ""       | "01000000000" || "이름을 입력해주세요."
        "test@naver.com" | "password" | "테스터"    | null          || "연락처를 입력해주세요."
        "test@naver.com" | "password" | "테스터"    | ""            || "연락처를 입력해주세요."
    }
}
