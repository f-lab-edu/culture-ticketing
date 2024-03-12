package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Round;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "회차 응답 DTO")
@Getter
public class RoundResponse {

    @Schema(description = "회차 아이디")
    private final Long roundId;
    @Schema(description = "회차 시작 일시")
    private final LocalDateTime roundStartDateTime;
    @Schema(description = "회차 종료 일시")
    private final LocalDateTime roundEndDateTime;

    public RoundResponse(Round round) {

        this.roundId = round.getRoundId();
        this.roundStartDateTime = round.getRoundStartDateTime();
        this.roundEndDateTime = round.getRoundEndDateTime();
    }
}
