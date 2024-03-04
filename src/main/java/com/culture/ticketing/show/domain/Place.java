package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
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
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "place")
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", nullable = false, updatable = false)
    private Long placeId;
    @Column(name = "place_name", nullable = false)
    private String placeName;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;
    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Builder
    public Place(Long placeId, String placeName, String address, BigDecimal latitude, BigDecimal longitude) {

        Objects.requireNonNull(latitude, "정확한 장소 위도를 입력해주세요.");
        Objects.requireNonNull(longitude, "정확한 장소 경도를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(address), "장소 주소를 입력해주세요.");
        Preconditions.checkArgument(latitude.compareTo(BigDecimal.valueOf(-90)) >= 0
                && latitude.compareTo(BigDecimal.valueOf(90)) <= 0, "장소 위도 범위를 벗어난 입력값입니다.");
        Preconditions.checkArgument(longitude.compareTo(BigDecimal.valueOf(-180)) >= 0
                && longitude.compareTo(BigDecimal.valueOf(180)) <= 0, "장소 경도 범위를 벗어난 입력값입니다.");

        this.placeId = placeId;
        this.placeName = placeName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
