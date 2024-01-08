package com.culture.ticketing.user.application.dto;

import com.culture.ticketing.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserSaveRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String userName;
    @NotBlank
    private String phoneNumber;

    @Builder
    public UserSaveRequest(String email, String password, String userName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
