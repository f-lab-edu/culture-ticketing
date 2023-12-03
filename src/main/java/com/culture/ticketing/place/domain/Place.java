package com.culture.ticketing.place.domain;

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

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_ADDRESS;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_LATITUDE;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_LONGITUDE;
import static com.culture.ticketing.common.response.BaseResponseStatus.PLACE_LATITUDE_OUT_OF_RANGE;
import static com.culture.ticketing.common.response.BaseResponseStatus.PLACE_LONGITUDE_OUT_OF_RANGE;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "place")
public class Place {

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
    public Place(String placeName, String address, BigDecimal latitude, BigDecimal longitude) {

        Objects.requireNonNull(latitude, EMPTY_PLACE_LATITUDE.getMessage());
        Objects.requireNonNull(longitude, EMPTY_PLACE_LONGITUDE.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(address), EMPTY_PLACE_ADDRESS.getMessage());
        Preconditions.checkArgument(latitude.compareTo(BigDecimal.valueOf(-90)) >= 0
                && latitude.compareTo(BigDecimal.valueOf(90)) <= 0, PLACE_LATITUDE_OUT_OF_RANGE.getMessage());
        Preconditions.checkArgument(longitude.compareTo(BigDecimal.valueOf(-180)) >= 0
                && longitude.compareTo(BigDecimal.valueOf(180)) <= 0, PLACE_LONGITUDE_OUT_OF_RANGE.getMessage());

        this.placeName = placeName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
