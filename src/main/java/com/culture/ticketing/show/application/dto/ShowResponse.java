package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.application.dto.PlaceResponse;
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
    private final String categoryName;
    private final LocalDate showStartDate;
    private final LocalDate showEndDate;
    private final String ageRestrictionName;
    private final int runningTime;
    private final String notice;
    private final String description;
    private final PlaceResponse place;

    @Builder
    public ShowResponse(Long showId, String showName, String posterImgUrl, String categoryName, LocalDate showStartDate, LocalDate showEndDate,
                        String ageRestrictionName, int runningTime, String notice, String description, PlaceResponse place) {
        this.showId = showId;
        this.showName = showName;
        this.posterImgUrl = posterImgUrl;
        this.categoryName = categoryName;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.ageRestrictionName = ageRestrictionName;
        this.runningTime = runningTime;
        this.notice = notice;
        this.description = description;
        this.place = place;
    }

    public static ShowResponse from(Show show, Place place) {
        return ShowResponse.builder()
                .showId(show.getShowId())
                .showName(show.getShowName())
                .posterImgUrl(show.getPosterImgUrl())
                .categoryName(show.getCategory().getCategoryName())
                .showStartDate(show.getShowStartDate())
                .showEndDate(show.getShowEndDate())
                .ageRestrictionName(show.getAgeRestriction().getAgeRestrictionName())
                .runningTime(show.getRunningTime())
                .notice(show.getNotice())
                .description(show.getDescription())
                .place(new PlaceResponse(place))
                .build();
    }
}
