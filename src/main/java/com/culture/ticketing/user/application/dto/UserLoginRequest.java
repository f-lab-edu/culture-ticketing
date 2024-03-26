package com.culture.ticketing.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 로그인 요청 DTO")
@Getter
@NoArgsConstructor
public class UserLoginRequest {

    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String password;

    @Builder
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
