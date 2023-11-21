package com.culture.ticketing.place.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import lombok.AccessLevel;
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

    @Column(name = "coordinate_x", nullable = false)
    private int coordinateX;

    @Column(name = "coordinate_y", nullable = false)
    private int coordinateY;

    @Column(name = "place_id", nullable = false)
    private Long placeId;


}
