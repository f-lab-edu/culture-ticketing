package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@SuppressWarnings("Duplicates")
public class ShowSaveRequest {

    private Category category;
    private String showName;
    private AgeRestriction ageRestriction;
    private int runningTime;
    private String notice;
    private String posterImgUrl;
    private String description;
    private LocalDate showStartDate;
    private LocalDate showEndDate;
    private Long placeId;

    @Builder
    public ShowSaveRequest(Category category, String showName, AgeRestriction ageRestriction, int runningTime, String notice,
                           String posterImgUrl, String description, LocalDate showStartDate, LocalDate showEndDate, Long placeId) {
        this.category = category;
        this.showName = showName;
        this.ageRestriction = ageRestriction;
        this.runningTime = runningTime;
        this.notice = notice;
        this.posterImgUrl = posterImgUrl;
        this.description = description;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.placeId = placeId;
    }

    public Show toEntity() {
        return Show.builder()
                .category(category)
                .showName(showName)
                .ageRestriction(ageRestriction)
                .runningTime(runningTime)
                .notice(notice)
                .posterImgUrl(posterImgUrl)
                .description(description)
                .showStartDate(showStartDate)
                .showEndDate(showEndDate)
                .placeId(placeId)
                .build();
    }

}
