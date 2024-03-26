package com.culture.ticketing.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
