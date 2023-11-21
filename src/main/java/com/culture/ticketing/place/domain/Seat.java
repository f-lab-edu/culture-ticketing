package com.culture.ticketing.place.domain;

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

        if (seatRow <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_ROW);
        }
        if (seatNumber <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_NUMBER);
        }
        if (areaId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_AREA_ID);
        }

        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.areaId = areaId;
    }
}
