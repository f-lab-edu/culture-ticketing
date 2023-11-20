package com.culture.ticketing.place.domain;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;

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

        if (!StringUtils.hasText(address)) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ADDRESS);
        }
        if (latitude == null || latitude.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_LATITUDE);
        }
        if (longitude == null || longitude.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_LONGITUDE);
        }

        this.placeName = placeName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
