package com.culture.ticketing.user.application

import com.culture.ticketing.user.application.dto.UserSaveRequest
import com.culture.ticketing.user.domain.User
import com.culture.ticketing.user.exception.DuplicatedUserEmailException
import com.culture.ticketing.user.infra.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceTest extends Specification {

    private UserRepository userRepository = Mock();
    private PasswordEncoder passwordEncoder = Mock();
    private UserService userService = new UserService(userRepository, passwordEncoder);

    def "유저 생성 성공"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();
        userRepository.findByEmail("test@naver.com") >> Optional.empty()

        when:
        userService.createUser(request);

        then:
        1 * userRepository.save(_) >> { args ->

            def savedUser = args.get(0) as User

            savedUser.email == "test@naver.com"
            savedUser.password == passwordEncoder.encode("password")
            savedUser.userName == "테스터"
            savedUser.phoneNumber == "01000000000"
        }
    }

    def "유저 생성 시 요청 값이 적절하지 않은 경우 예외 발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .build();

        when:
        userService.createUser(request);

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

    def "유저 생성 시 유저 이메일 동일한 유저가 이미 존재하는 경우 예외 발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        userRepository.findByEmail("test@naver.com") >> Optional.of(request.toEntity(passwordEncoder))

        when:
        userService.createUser(request)

        then:
        def e = thrown(DuplicatedUserEmailException.class)
        e.message == "이미 등록된 이메일입니다."
    }

    def "유저 아이디로 유저 존재 여부 확인"() {

        given:
        Long userId = 1L;
        userRepository.existsById(userId) >> true

        when:
        boolean response = userService.notExistsById(userId);

        then:
        !response
    }
}
