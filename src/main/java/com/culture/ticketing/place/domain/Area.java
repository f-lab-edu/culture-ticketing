package com.culture.ticketing.place.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    @Column(name = "place_id", nullable = false)
    private Long placeId;

    @Builder
    public Area(Long areaId, String areaName, Long placeId) {

        Objects.requireNonNull(placeId, "장소 아이디를 입력해주세요.");

        this.areaId = areaId;
        this.areaName = areaName;
        this.placeId = placeId;
    }
}
