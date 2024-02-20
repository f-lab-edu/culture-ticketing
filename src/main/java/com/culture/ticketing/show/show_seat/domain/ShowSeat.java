package com.culture.ticketing.show.show_seat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_seat")
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_seat_id", nullable = false, updatable = false)
    private Long showSeatId;

    @Column(name = "show_seat_grade_id", nullable = false)
    private Long showSeatGradeId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Builder
    public ShowSeat(Long showSeatId, Long showSeatGradeId, Long seatId) {

        Objects.requireNonNull(showSeatGradeId, "공연 좌석 등급 아이디를 입력해주세요.");
        Objects.requireNonNull(seatId, "좌석 아이디를 입력해주세요.");

        this.showSeatId = showSeatId;
        this.showSeatGradeId = showSeatGradeId;
        this.seatId = seatId;
    }
}
