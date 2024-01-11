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
    public ShowSeatGrade(Long showSeatGradeId, String seatGrade, int price, Long showId) {

        Objects.requireNonNull(showId, "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(seatGrade), "공연 좌석 등급을 입력해주세요.");
        Preconditions.checkArgument(price >= 0, "공연 좌석 가격을 0 이상으로 입력해주세요.");

        this.showSeatGradeId = showSeatGradeId;
        this.seatGrade = seatGrade;
        this.price = price;
        this.showId = showId;
    }
}
