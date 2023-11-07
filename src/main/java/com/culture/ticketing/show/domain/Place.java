package com.culture.ticketing.show.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    private String placeName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
