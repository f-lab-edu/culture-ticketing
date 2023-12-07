package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SHOW_SEAT_PRICE;

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

        Objects.requireNonNull(showId, EMPTY_SHOW_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(seatGrade), EMPTY_SHOW_SEAT_GRADE.getMessage());
        Preconditions.checkArgument(price > 0, NEGATIVE_SHOW_SEAT_PRICE.getMessage());

        this.seatGrade = seatGrade;
        this.price = price;
        this.showId = showId;
    }
}
