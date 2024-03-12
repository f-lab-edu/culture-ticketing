package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Show;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "공연 응답 DTO")
@Getter
public class ShowResponse {

    @Schema(description = "공연 아이디")
    private final Long showId;
    @Schema(description = "공연명")
    private final String showName;
    @Schema(description = "포스터 이미지 url")
    private final String posterImgUrl;
    @Schema(description = "카테고리명")
    private final String categoryName;
    @Schema(description = "공연 시작일")
    private final LocalDate showStartDate;
    @Schema(description = "공연 종료일")
    private final LocalDate showEndDate;
    @Schema(description = "관람연령제한가")
    private final String ageRestrictionName;
    @Schema(description = "러닝타임")
    private final int runningTime;
    @Schema(description = "공지")
    private final String notice;
    @Schema(description = "공연 상세 설명")
    private final String description;
    @Schema(description = "장소 정보")
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
