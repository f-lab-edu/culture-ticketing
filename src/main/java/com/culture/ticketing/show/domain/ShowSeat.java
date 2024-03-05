package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
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
public class ShowSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_seat_id", nullable = false, updatable = false)
    private Long showSeatId;

    @Column(name = "show_seat_row")
    private String showSeatRow;

    @Column(name = "show_seat_number", nullable = false)
    private int showSeatNumber;

    @Column(name = "show_area_id", nullable = false)
    private Long showAreaId;

    @Builder
    public ShowSeat(Long showSeatId, String showSeatRow, int showSeatNumber, Long showAreaId) {

        Objects.requireNonNull(showAreaId, "공연 구역 아이디를 입력해주세요.");
        Preconditions.checkArgument(showSeatNumber > 0, "좌석 번호를 1 이상 숫자로 입력해주세요.");

        this.showSeatId = showSeatId;
        this.showSeatRow = showSeatRow;
        this.showSeatNumber = showSeatNumber;
        this.showAreaId = showAreaId;
    }
}
