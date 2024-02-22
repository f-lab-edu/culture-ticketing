package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Round;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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

    @Builder
    public RoundResponse(Long roundId, LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime) {
        this.roundId = roundId;
        this.roundStartDateTime = roundStartDateTime;
        this.roundEndDateTime = roundEndDateTime;
    }

    public static RoundResponse from(Round round) {

        return RoundResponse.builder()
                .roundId(round.getRoundId())
                .roundStartDateTime(round.getRoundStartDateTime())
                .roundEndDateTime(round.getRoundEndDateTime())
                .build();
    }
}
