package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "공연 구역 등급 목록 응답 DTO")
public class ShowAreaGradesResponse {

    @Schema(description = "공연 구역 등급 목록")
    private final List<ShowAreaGradeResponse> showAreaGrades;

    public ShowAreaGradesResponse(List<ShowAreaGrade> showAreaGrades) {
        this.showAreaGrades = showAreaGrades.stream()
                .map(ShowAreaGradeResponse::new)
                .collect(Collectors.toList());
        ;
    }

    public List<ShowAreaGradeResponse> getShowAreaGrades() {
        return Collections.unmodifiableList(showAreaGrades);
    }

    public ShowAreaGradeResponse getByShowAreaGradeId(Long showAreaGradeId) {
        return this.showAreaGrades.stream()
                .filter(showAreaGrade -> showAreaGrade.getShowAreaGradeId().equals(showAreaGradeId))
                .findAny()
                .orElseThrow(() -> {
                    throw new ShowAreaNotFoundException(showAreaGradeId);
                });
    }
}
