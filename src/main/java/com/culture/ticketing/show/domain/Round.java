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

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_ROUND_DATE_TIME;

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
    public Round(LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime, Long showId) {

        Objects.requireNonNull(showId, EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(roundStartDateTime, EMPTY_ROUND_DATE_TIME.getMessage());
        Objects.requireNonNull(roundEndDateTime, EMPTY_ROUND_DATE_TIME.getMessage());

        this.roundStartDateTime = roundStartDateTime;
        this.roundEndDateTime = roundEndDateTime;
        this.showId = showId;
    }
}
