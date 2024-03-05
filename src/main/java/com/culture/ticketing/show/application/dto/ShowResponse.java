package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Show;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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

    public static ShowResponse from(Show show, PlaceResponse place) {
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
                .place(place)
                .build();
    }
}
