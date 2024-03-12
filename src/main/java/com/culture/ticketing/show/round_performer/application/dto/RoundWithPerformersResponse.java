package com.culture.ticketing.show.round_performer.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "회차 및 출연자 목록 응답 DTO")
@Getter
public class RoundWithPerformersResponse {

    @Schema(description = "회차 아이디")
    private final Long roundId;
    @Schema(description = "회차 시작 일시")
    private final LocalDateTime roundStartDateTime;
    @Schema(description = "회차 종료 일시")
    private final LocalDateTime roundEndDateTime;
    @Schema(description = "출연자 목록 정보")
    private final List<PerformerResponse> performers;

    public RoundWithPerformersResponse(RoundResponse round, List<PerformerResponse> performers) {
        this.roundId = round.getRoundId();
        this.roundStartDateTime = round.getRoundStartDateTime();
        this.roundEndDateTime = round.getRoundEndDateTime();
        this.performers = performers;
    }
}
