package com.culture.ticketing.place.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "area")
public class Area extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id", nullable = false, updatable = false)
    private Long areaId;

    @Column(name = "area_name", nullable = false)
    private String areaName;

    @Column(name = "coordinate_x", nullable = false)
    private int coordinateX;

    @Column(name = "coordinate_y", nullable = false)
    private int coordinateY;

    @Column(name = "place_id", nullable = false)
    private Long placeId;

    @Builder
    public Area(String areaName, int coordinateX, int coordinateY, Long placeId) {

        if (placeId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ID);
        }

        this.areaName = areaName;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.placeId = placeId;
    }
}
