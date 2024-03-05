package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowAreaSaveRequest {

    private String showAreaName;
    private Long showId;
    private Long showAreaGradeId;

    @Builder
    public ShowAreaSaveRequest(String showAreaName, Long showId, Long showAreaGradeId) {
        this.showAreaName = showAreaName;
        this.showId = showId;
        this.showAreaGradeId = showAreaGradeId;
    }

    public ShowArea toEntity() {
        return ShowArea.builder()
                .showAreaName(showAreaName)
                .showId(showId)
                .showAreaGradeId(showAreaGradeId)
                .build();
    }
}
