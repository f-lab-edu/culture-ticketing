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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "round_performer")
public class RoundPerformer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_performer_id", nullable = false, updatable = false)
    private Long roundPerformerId;
    @Column(name = "round_id", nullable = false)
    private Long roundId;
    @Column(name = "performer_id", nullable = false)
    private Long performerId;

    @Builder
    public RoundPerformer(Long roundId, Long performerId) {
        this.roundId = roundId;
        this.performerId = performerId;
    }
}
