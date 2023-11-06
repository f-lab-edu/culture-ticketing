package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSaveRequest {

    private String categoryCd;
    private String showName;
    private String ageRestrictionCd;
    private String placeName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate showStartDate;
    private LocalDate showEndDate;
    private int runningTime;
    private String notice;
    private String posterImgUrl;
    private String description;

}
