package com.culture.ticketing.show.domain;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_FLOOR_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SHOW_FLOOR_COUNT;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_floor")
public class ShowFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_floor_id", nullable = false, updatable = false)
    private Long showFloorId;

    @Column(name = "show_floor_name", nullable = false)
    private String showFloorName;

    @Column(name = "coordinate_x", nullable = false)
    private int coordinateX;

    @Column(name = "coordinate_y", nullable = false)
    private int coordinateY;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "show_seat_grade_id", nullable = false)
    private Long showSeatGradeId;

    @Builder
    public ShowFloor(String showFloorName, int coordinateX, int coordinateY, int count, Long showSeatGradeId) {

        Objects.requireNonNull(showSeatGradeId, EMPTY_SHOW_SEAT_GRADE_ID.getMessage(showSeatGradeId));
        Preconditions.checkArgument(StringUtils.hasText(showFloorName), EMPTY_SHOW_FLOOR_NAME.getMessage());
        Preconditions.checkArgument(count > 0, NEGATIVE_SHOW_FLOOR_COUNT.getMessage());

        this.showFloorName = showFloorName;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.count = count;
        this.showSeatGradeId = showSeatGradeId;
    }
}
