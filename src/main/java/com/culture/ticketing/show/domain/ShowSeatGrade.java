package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_seat_grade")
public class ShowSeatGrade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_seat_grade_id", nullable = false, updatable = false)
    private Long showSeatGradeId;

    @Column(name = "seat_type", nullable = false)
    private String seatGrade;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Builder
    public ShowSeatGrade(String seatGrade, int price, Long showId) {

        if (!StringUtils.hasText(seatGrade)) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE);
        }
        if (price < 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SHOW_SEAT_PRICE);
        }
        if (showId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_ID);
        }

        this.seatGrade = seatGrade;
        this.price = price;
        this.showId = showId;
    }
}
