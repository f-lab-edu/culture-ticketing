package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ShowSaveRequest {

    @NotBlank(message = "카테고리를 입력해주세요.")
    private String categoryCd;
    @NotBlank(message = "공연명을 입력해주세요.")
    private String showName;
    @NotBlank(message = "관람등급을 입력해주세요.")
    private String ageRestrictionCd;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate showStartDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate showEndDate;
    @Positive
    private int runningTime;
    private String notice;
    @NotBlank(message = "포스터 이미지를 입력해주세요.")
    private String posterImgUrl;
    private String description;
    @NotNull
    private Long placeId;

    public Show toEntity(Place place) {
        return Show.builder()
                .category(Category.valueOf(categoryCd))
                .showName(showName)
                .ageRestriction(AgeRestriction.valueOf(ageRestrictionCd))
                .showStartDate(showStartDate)
                .showEndDate(showEndDate)
                .runningTime(runningTime)
                .notice(notice)
                .posterImgUrl(posterImgUrl)
                .description(description)
                .place(place)
                .build();
    }

}
