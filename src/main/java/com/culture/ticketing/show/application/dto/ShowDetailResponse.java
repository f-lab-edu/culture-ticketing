package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "공연 상세 응답 DTO")
@Getter
public class ShowDetailResponse {

    @Schema(description = "공연 정보")
    private final ShowResponse show;
    @Schema(description = "회차 및 출연자 목록 정보")
    private final List<RoundWithPerformersResponse> roundsWithPerformers;
    @Schema(description = "공연 구역 등급 목록 정보")
    private final List<ShowAreaGradeResponse> showAreaGrades;

    @Builder
    public ShowDetailResponse(ShowResponse show, RoundsWithPerformersResponse roundsWithPerformers, ShowAreaGradesResponse showAreaGrades) {
        this.show = show;
        this.roundsWithPerformers = roundsWithPerformers.getRoundWithPerformers();
        this.showAreaGrades = showAreaGrades.getShowAreaGrades();
    }

    public static ShowDetailResponse from(Show show, PlaceResponse place, RoundsWithPerformersResponse roundsWithPerformers, ShowAreaGradesResponse showAreaGrades) {
        return ShowDetailResponse.builder()
                .show(ShowResponse.from(show, place))
                .roundsWithPerformers(roundsWithPerformers)
                .showAreaGrades(showAreaGrades)
                .build();
    }
}
