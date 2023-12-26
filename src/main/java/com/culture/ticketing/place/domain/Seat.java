package com.culture.ticketing.place.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SEAT_NUMBER;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SEAT_ROW;

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

        Objects.requireNonNull(areaId, EMPTY_PLACE_ID.getMessage());
        Preconditions.checkArgument(seatRow > 0, NEGATIVE_SEAT_ROW.getMessage());
        Preconditions.checkArgument(seatNumber > 0, NEGATIVE_SEAT_NUMBER.getMessage());

        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.areaId = areaId;
    }
}
