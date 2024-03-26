package com.culture.ticketing.user.domain;

import lombok.Getter;

@Getter
public enum Role {

    USER("일반 회원"),
    ADMIN("관리자");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
