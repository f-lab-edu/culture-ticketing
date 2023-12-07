package com.culture.ticketing.show.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SEAT_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE_ID;

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

    @ColumnDefault("false")
    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    @Builder
    public ShowSeat(Long showSeatGradeId, Long seatId, boolean isHidden) {

        Objects.requireNonNull(showSeatGradeId, EMPTY_SHOW_SEAT_GRADE_ID.getMessage());
        Objects.requireNonNull(seatId, EMPTY_SEAT_ID.getMessage());

        this.showSeatGradeId = showSeatGradeId;
        this.seatId = seatId;
        this.isHidden = isHidden;
    }
}
