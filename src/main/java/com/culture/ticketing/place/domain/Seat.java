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

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "seat_row", nullable = false)
    private int seatRow;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "place_id", nullable = false)
    private Long placeId;

    @Builder
    public Seat(int coordinateX, int coordinateY, String area, int seatRow, int seatNumber, Long placeId) {

        if (!StringUtils.hasText(area)) {
            throw new BaseException(BaseResponseStatus.EMPTY_SEAT_AREA);
        }
        if (seatRow <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_ROW);
        }
        if (seatNumber <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_NUMBER);
        }
        if (placeId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ID);
        }

        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.area = area;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.placeId = placeId;
    }
}
