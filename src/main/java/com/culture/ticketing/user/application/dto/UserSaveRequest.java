package com.culture.ticketing.user.application.dto;

import com.culture.ticketing.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserSaveRequest {

    private String email;
    private String password;
    private String userName;
    private String phoneNumber;

    @Builder
    public UserSaveRequest(String email, String password, String userName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .passwordEncoder(passwordEncoder)
                .build();
    }
}
