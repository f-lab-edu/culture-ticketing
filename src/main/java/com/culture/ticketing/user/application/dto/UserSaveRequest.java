package com.culture.ticketing.user.application.dto;

import com.culture.ticketing.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Schema(description = "회원 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class UserSaveRequest {

    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String password;
    @Schema(description = "회원 이름")
    private String userName;
    @Schema(description = "연락처")
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
