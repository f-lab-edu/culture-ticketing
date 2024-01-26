package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
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
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "round")
public class Round extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id", nullable = false, updatable = false)
    private Long roundId;
    @Column(name = "round_start_date_time", nullable = false)
    private LocalDateTime roundStartDateTime;
    @Column(name = "round_end_date_time", nullable = false)
    private LocalDateTime roundEndDateTime;
    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Builder
    public Round(Long roundId, LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime, Long showId) {

        Objects.requireNonNull(showId, "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(roundStartDateTime, "회차 시작 일시를 입력해주세요.");
        Objects.requireNonNull(roundEndDateTime, "회차 종료 일시를 입력해주세요.");

        this.roundId = roundId;
        this.roundStartDateTime = roundStartDateTime;
        this.roundEndDateTime = roundEndDateTime;
        this.showId = showId;
    }
}
