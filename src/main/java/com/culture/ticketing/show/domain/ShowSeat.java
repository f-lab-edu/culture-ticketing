package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

        if (showSeatGradeId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE_ID);
        }
        if (seatId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SEAT_ID);
        }

        this.showSeatGradeId = showSeatGradeId;
        this.seatId = seatId;
        this.isHidden = isHidden;
    }
}
