package com.culture.ticketing.user.domain

import spock.lang.Specification

class UserTest extends Specification {

    def "유저_이메일이_null_인_경우_예외_발생"() {

        when:
        User.builder()
                .email(null)
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이메일을 입력해주세요."
    }

    def "유저_이메일이_빈_값인_경우_예외_발생"() {

        when:
        User.builder()
                .email("")
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이메일을 입력해주세요."
    }

    def "비밀번호가_null_인_경우_예외_발생"() {

        when:
        User.builder()
                .email("test@naver.com")
                .password(null)
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "비밀번호를 입력해주세요."
    }

    def "비밀번호가_빈_값인_경우_예외_발생"() {

        when:
        User.builder()
                .email("test@naver.com")
                .password("")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "비밀번호를 입력해주세요."
    }

    def "유저_이름이_null_인_경우_예외_발생"() {

        when:
        User.builder()
                .email("test@naver.com")
                .password("password")
                .userName(null)
                .phoneNumber("01000000000")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이름을 입력해주세요."
    }

    def "유저_이름이_빈_값인_경우_예외_발생"() {

        when:
        User.builder()
                .email("test@naver.com")
                .password("password")
                .userName("")
                .phoneNumber("01000000000")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이름을 입력해주세요."
    }

    def "연락처가_null_인_경우_예외_발생"() {

        when:
        User.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber(null)
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "연락처를 입력해주세요."
    }

    def "연락처가_빈_값인_경우_예외_발생"() {

        when:
        User.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber("")
                .build();

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "연락처를 입력해주세요."
    }
}
