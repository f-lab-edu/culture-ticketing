package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "공연 상세 응답 DTO")
@Getter
public class ShowDetailResponse {

    @Schema(description = "공연 정보")
    private final ShowResponse show;
    @Schema(description = "공연 좋아요 수")
    private final int showLikeCnt;
    @Schema(description = "회차 및 출연자 목록 정보")
    private final List<RoundWithPerformersResponse> roundsWithPerformers;
    @Schema(description = "공연 구역 등급 목록 정보")
    private final List<ShowAreaGradeResponse> showAreaGrades;

    public ShowDetailResponse(ShowResponse show, int showLikeCnt, List<RoundWithPerformersResponse> roundsWithPerformers, List<ShowAreaGradeResponse> showAreaGrades) {

        this.show = show;
        this.showLikeCnt = showLikeCnt;
        this.roundsWithPerformers = roundsWithPerformers;
        this.showAreaGrades = showAreaGrades;
    }
}
