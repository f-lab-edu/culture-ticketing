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

    @NotBlank
    private String categoryCd;
    @NotBlank
    private String showName;
    @NotBlank
    private String ageRestrictionCd;
    private String placeName;
    @NotBlank
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
    @NotBlank
    private String posterImgUrl;
    private String description;

}
