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

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_AREA_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_AREA_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_area")
public class ShowArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_area_id", nullable = false, updatable = false)
    private Long showAreaId;

    @Column(name = "show_area_name", nullable = false)
    private String showAreaName;

    @Column(name = "coordinate_x", nullable = false)
    private int coordinateX;

    @Column(name = "coordinate_y", nullable = false)
    private int coordinateY;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Column(name = "area_id", nullable = false)
    private Long areaId;

    @Builder
    public ShowArea(String showAreaName, int coordinateX, int coordinateY, Long showId, Long areaId) {

        Objects.requireNonNull(showId, EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(areaId, EMPTY_AREA_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(showAreaName), EMPTY_SHOW_AREA_NAME.getMessage());

        this.showAreaName = showAreaName;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.showId = showId;
        this.areaId = areaId;
    }
}
