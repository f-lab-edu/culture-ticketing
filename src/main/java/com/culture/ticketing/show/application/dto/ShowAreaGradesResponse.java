package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAreaGradesResponse {

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
