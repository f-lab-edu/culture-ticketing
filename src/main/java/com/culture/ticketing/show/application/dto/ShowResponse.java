package com.culture.ticketing.show.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ShowResponse {

    private final Long showId;
    private final String posterImgUrl;
    private final LocalDate showStartDate;
    private final LocalDate showEndDate;
    private final String placeName;

    @Builder
    public ShowResponse(Long showId, String posterImgUrl, LocalDate showStartDate, LocalDate showEndDate, String placeName) {
        this.showId = showId;
        this.posterImgUrl = posterImgUrl;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.placeName = placeName;
    }
}
