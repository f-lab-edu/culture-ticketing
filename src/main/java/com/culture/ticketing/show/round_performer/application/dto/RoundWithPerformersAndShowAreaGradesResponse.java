package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.application.dto.ShowSeatCountResponse;
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "회차 및 출연자, 공연 구역 등급 정보 응답 DTO")
@Getter
public class RoundWithPerformersAndShowAreaGradesResponse {

    @Schema(description = "회차 아이디")
    private final Long roundId;
    @Schema(description = "회차 시작 일시")
    private final LocalDateTime roundStartDateTime;
    @Schema(description = "회차 종료 일시")
    private final LocalDateTime roundEndDateTime;
    @Schema(description = "출연자 목록")
    private final List<PerformerResponse> performers;
    @Schema(description = "공연 구역 등급별 이용 가능 공연 좌석 갯수 목록")
    private final List<ShowSeatCountResponse> showSeatCounts;

    public RoundWithPerformersAndShowAreaGradesResponse(RoundWithPerformersResponse roundWithPerformers, ShowSeatCountsResponse showSeatCounts) {
        this.roundId = roundWithPerformers.getRoundId();
        this.roundStartDateTime = roundWithPerformers.getRoundStartDateTime();
        this.roundEndDateTime = roundWithPerformers.getRoundEndDateTime();
        this.performers = roundWithPerformers.getPerformers();
        this.showSeatCounts = showSeatCounts.getShowSeatCounts();
    }
}
