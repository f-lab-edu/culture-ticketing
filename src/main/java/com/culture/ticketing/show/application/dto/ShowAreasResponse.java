package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAreasResponse {

    private final List<ShowAreaResponse> showAreas;

    public ShowAreasResponse(List<ShowArea> showAreas, ShowAreaGradesResponse showAreaGrades) {
        this.showAreas = showAreas.stream()
                .map(showArea -> new ShowAreaResponse(showArea, showAreaGrades.getByShowAreaGradeId(showArea.getShowAreaGradeId())))
                .collect(Collectors.toList());
    }

    public List<ShowAreaResponse> getShowAreas() {
        return Collections.unmodifiableList(this.showAreas);
    }

    public ShowAreaResponse getByShowAreaId(Long showAreaId) {
        return this.showAreas.stream()
                .filter(showArea -> showArea.getShowAreaId().equals(showArea.getShowAreaId()))
                .findAny()
                .orElseThrow(() -> {
                    throw new ShowAreaNotFoundException(showAreaId);
                });
    }
}
