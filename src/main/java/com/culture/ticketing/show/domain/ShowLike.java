package com.culture.ticketing.show.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_like")
public class ShowLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_like_id", nullable = false, updatable = false)
    private Long showLikeId;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public ShowLike(Long showLikeId, Long showId, Long userId) {

        Objects.requireNonNull(showId, "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(userId, "유저 아이디를 입력해주세요.");

        this.showLikeId = showLikeId;
        this.showId = showId;
        this.userId = userId;
    }
}
