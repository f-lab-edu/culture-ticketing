package com.culture.ticketing.notification.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
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
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, updatable = false)
    private Long notificationId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "readDateTime")
    private LocalDateTime readDateTime;

    @Builder
    public Notification(Long notificationId, Long userId, String message, LocalDateTime readDateTime) {

        Objects.requireNonNull(userId, "유저 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(message), "알림 메세지 내용을 입력해주세요.");

        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.readDateTime = readDateTime;
    }
}
