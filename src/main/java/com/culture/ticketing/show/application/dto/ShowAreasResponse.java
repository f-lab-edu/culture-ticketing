package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "공연 구역 목록 응답 DTO")
public class ShowAreasResponse {

    @Schema(description = "공연 구역 목록")
    private final List<ShowAreaResponse> showAreas;

    public ShowAreasResponse(List<ShowArea> showAreas, ShowAreaGradesResponse showAreaGrades) {
        this.showAreas = showAreas.stream()
                .map(showArea -> new ShowAreaResponse(showArea, showAreaGrades.getByShowAreaGradeId(showArea.getShowAreaGradeId())))
                .collect(Collectors.toList());
    }

    public List<ShowAreaResponse> getShowAreas() {
        return Collections.unmodifiableList(this.showAreas);
    }

    public List<Long> getShowAreaIds() {
        return this.showAreas.stream()
                .map(ShowAreaResponse::getShowAreaId).collect(Collectors.toUnmodifiableList());
    }

    public ShowAreaResponse getByShowAreaId(Long showAreaId) {
        return this.showAreas.stream()
                .filter(showArea -> showArea.getShowAreaId().equals(showAreaId))
                .findAny()
                .orElseThrow(() -> {
                    throw new ShowAreaNotFoundException(showAreaId);
                });
    }
}
