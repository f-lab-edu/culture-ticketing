package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공연 구역 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowAreaSaveRequest {

    @Schema(description = "공연 구역명")
    private String showAreaName;
    @Schema(description = "공연 아이디")
    private Long showId;
    @Schema(description = "공연 구역 등급 아이디")
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
