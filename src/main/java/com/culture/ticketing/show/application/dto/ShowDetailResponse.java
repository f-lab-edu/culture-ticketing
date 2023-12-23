package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ShowDetailResponse {

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
    private final List<RoundResponse> rounds;

    @Builder
    public ShowDetailResponse(Long showId, String showName, String posterImgUrl, String categoryName, LocalDate showStartDate, LocalDate showEndDate,
                              String ageRestrictionName, int runningTime, String notice, String description, PlaceResponse place, List<RoundResponse> rounds) {
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
        this.rounds = rounds;
    }

    public static ShowDetailResponse from(Show show, Place place, List<RoundResponse> rounds) {
        return ShowDetailResponse.builder()
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
                .rounds(rounds)
                .build();
    }
}
