package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.domain.Show;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ShowResponse {

    private final Long showId;
    private final String showName;
    private final String posterImgUrl;
    private final LocalDate showStartDate;
    private final LocalDate showEndDate;
    private final String placeName;

    @Builder
    public ShowResponse(Long showId, String showName, String posterImgUrl, LocalDate showStartDate, LocalDate showEndDate, String placeName) {
        this.showId = showId;
        this.showName = showName;
        this.posterImgUrl = posterImgUrl;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.placeName = placeName;
    }

    public static ShowResponse create(Show show, Place place) {
        return ShowResponse.builder()
                .showId(show.getShowId())
                .showName(show.getShowName())
                .posterImgUrl(show.getPosterImgUrl())
                .showStartDate(show.getShowStartDate())
                .showEndDate(show.getShowEndDate())
                .placeName(place.getPlaceName())
                .build();
    }
}
