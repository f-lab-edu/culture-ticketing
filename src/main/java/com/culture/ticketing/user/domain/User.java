package com.culture.ticketing.user.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Builder
    public User(Long userId, String email, String password, String userName, String phoneNumber) {

        Preconditions.checkArgument(StringUtils.hasText(email), "이메일을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(password), "비밀번호를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(userName), "이름을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(phoneNumber), "연락처를 입력해주세요.");

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public void changePassword(String password) {
        Preconditions.checkArgument(StringUtils.hasText(password), "비밀번호를 입력해주세요.");
        this.password = password;
    }
}
