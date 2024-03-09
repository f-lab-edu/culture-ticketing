package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Show;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ShowResponse {

    private final Long showId;
    private final String showName;
    private final String posterImgUrl;
    private final String categoryName;
    private final LocalDate showStartDate;
    private final LocalDate showEndDate;
    private final String ageRestrictionName;
    private final int runningTime;
    private final String notice;
    private final String description;
    private final PlaceResponse place;

    public ShowResponse(Show show, PlaceResponse place) {

        this.showId = show.getShowId();
        this.showName = show.getShowName();
        this.posterImgUrl = show.getPosterImgUrl();
        this.categoryName = show.getCategory().getCategoryName();
        this.showStartDate = show.getShowStartDate();
        this.showEndDate = show.getShowEndDate();
        this.ageRestrictionName = show.getAgeRestriction().getAgeRestrictionName();
        this.runningTime = show.getRunningTime();
        this.notice = show.getNotice();
        this.description = show.getDescription();
        this.place = place;
    }

    public Long getPlaceId() {

        return place.getPlaceId();
    }
}
