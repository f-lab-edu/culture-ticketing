package com.culture.ticketing.show.application.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSaveRequest {

    @NotBlank(message = "카테고리를 입력해주세요.")
    private String categoryCd;
    @NotBlank(message = "공연명을 입력해주세요.")
    private String showName;
    @NotBlank(message = "관람등급을 입력해주세요.")
    private String ageRestrictionCd;
    private String placeName;
    @NotBlank(message = "공연 장소 주소를 입력해주세요.")
    private String address;
    @NotNull
    @Positive
    private BigDecimal latitude;
    @NotNull
    @Positive
    private BigDecimal longitude;
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

}
