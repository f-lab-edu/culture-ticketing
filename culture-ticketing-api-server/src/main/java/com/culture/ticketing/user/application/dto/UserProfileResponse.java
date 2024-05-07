package com.culture.ticketing.user.application.dto;

import com.culture.ticketing.user.domain.Role;
import com.culture.ticketing.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "회원 생성 요청 DTO")
@Getter
public class UserProfileResponse {

    @Schema(description = "유저 아이디")
    private final Long userId;
    @Schema(description = "이메일")
    private final String email;
    @Schema(description = "회원 이름")
    private final String userName;
    @Schema(description = "연락처")
    private final String phoneNumber;
    @Schema(description = "권한")
    private final Role role;

    @Builder
    public UserProfileResponse(Long userId, String email, String userName, String phoneNumber, Role role) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static UserProfileResponse from(User user) {

        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
