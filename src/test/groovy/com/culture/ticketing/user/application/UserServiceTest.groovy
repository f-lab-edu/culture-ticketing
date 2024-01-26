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

    def "유저_생성_시_이메일이_null_인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email(null)
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이메일을 입력해주세요."
    }

    def "유저_생성_시_이메일이_빈_값인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("")
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이메일을 입력해주세요."
    }

    def "유저_생성_시_비밀번호가_null_인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password(null)
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "비밀번호를 입력해주세요."
    }

    def "유저_생성_시_비밀번호가_빈_값인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "비밀번호를 입력해주세요."
    }

    def "유저_생성_시_유저_이름이_null_인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName(null)
                .phoneNumber("01000000000")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이름을 입력해주세요."
    }

    def "유저_생성_시_유저_이름이_빈_값인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("")
                .phoneNumber("01000000000")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이름을 입력해주세요."
    }

    def "유저_생성_시_연락처가_null_인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber(null)
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "연락처를 입력해주세요."
    }

    def "유저_생성_시_연락처가_빈_값인_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber("")
                .build();

        when:
        userService.createUser(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "연락처를 입력해주세요."
    }

    def "유저_생성_시_유저_이메일_동일한_유저가_이미_존재하는_경우_예외_발생"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        userRepository.findByEmail("test@naver.com") >> Optional.of(request.toEntity())

        when:
        userService.createUser(request)

        then:
        def e = thrown(DuplicatedUserEmailException.class)
        e.message == "이미 등록된 이메일입니다."
    }
}
