package com.culture.ticketing.user

import com.culture.ticketing.user.domain.Role
import com.culture.ticketing.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder;

class UserFixtures {

    static User createUser(Map map = [:], PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(map.getOrDefault("userId", null) as Long)
                .email(map.getOrDefault("email", "test@naver.com") as String)
                .password(map.getOrDefault("password", "password") as String)
                .userName(map.getOrDefault("userName", "test") as String)
                .phoneNumber(map.getOrDefault("phoneNumber", "01000000000") as String)
                .role(map.getOrDefault("role", Role.USER) as Role)
                .passwordEncoder(passwordEncoder)
                .build();
    }
}
