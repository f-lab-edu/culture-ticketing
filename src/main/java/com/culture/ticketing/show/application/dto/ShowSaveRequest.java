package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "공연 생성 요청 DTO")
@Getter
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShowSaveRequest {

    @Schema(description = "카테고리")
    private Category category;
    @Schema(description = "공연명")
    private String showName;
    @Schema(description = "관람연령제한가")
    private AgeRestriction ageRestriction;
    @Schema(description = "러닝타임")
    private int runningTime;
    @Schema(description = "공지")
    private String notice;
    @Schema(description = "포스터 이미지 url")
    private String posterImgUrl;
    @Schema(description = "공연 상세 설명")
    private String description;
    @Schema(description = "공연 시작일")
    private LocalDate showStartDate;
    @Schema(description = "공연 종료일")
    private LocalDate showEndDate;
    @Schema(description = "장소 아이디")
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
