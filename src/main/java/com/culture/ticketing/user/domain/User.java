package com.culture.ticketing.user.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.user.exception.PasswordNotMatchException;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(Long userId, String email, String password, String userName, String phoneNumber, Role role, PasswordEncoder passwordEncoder) {

        Objects.requireNonNull(role, "권한을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(email), "이메일을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(password), "비밀번호를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(userName), "이름을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(phoneNumber), "연락처를 입력해주세요.");

        this.userId = userId;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        Preconditions.checkArgument(StringUtils.hasText(password), "비밀번호를 입력해주세요.");
        this.password = passwordEncoder.encode(password);
    }

    public void checkPassword(String password, PasswordEncoder passwordEncoder) {

        if (!passwordEncoder.matches(password, this.password)) {
            throw new PasswordNotMatchException();
        }
    }
}
