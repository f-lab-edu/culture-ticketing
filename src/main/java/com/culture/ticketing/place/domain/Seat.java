package com.culture.ticketing.place.domain;

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
@Table(name = "seat")
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false, updatable = false)
    private Long seatId;

    @Column(name = "coordinate_x", nullable = false)
    private int coordinateX;

    @Column(name = "coordinate_y", nullable = false)
    private int coordinateY;

    @Column(name = "seat_row", nullable = false)
    private int seatRow;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "area_id", nullable = false)
    private Long areaId;

    @Builder
    public Seat(int coordinateX, int coordinateY, int seatRow, int seatNumber, Long areaId) {

        Objects.requireNonNull(areaId, "장소 아이디를 입력해주세요.");
        Preconditions.checkArgument(seatRow > 0, "좌석 행을 1 이상 숫자로 입력해주세요.");
        Preconditions.checkArgument(seatNumber > 0, "좌석 번호를 1 이상 숫자로 입력해주세요.");

        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.areaId = areaId;
    }
}
