package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ShowSaveRequest {

    @NotNull
    private Category category;
    @NotBlank(message = "공연명을 입력해주세요.")
    private String showName;
    @NotNull
    private AgeRestriction ageRestriction;
    @Positive
    private int runningTime;
    private String notice;
    @NotBlank(message = "포스터 이미지를 입력해주세요.")
    private String posterImgUrl;
    private String description;
    @NotNull
    private LocalDate showStartDate;
    @NotNull
    private LocalDate showEndDate;
    @NotNull
    private Long placeId;

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
