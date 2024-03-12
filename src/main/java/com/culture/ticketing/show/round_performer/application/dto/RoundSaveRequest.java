package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.round_performer.domain.Round;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "회차 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class RoundSaveRequest {

    @Schema(description = "공연 아이디")
    private Long showId;
    @Schema(description = "회차 시작 일시")
    private LocalDateTime roundStartDateTime;

    @Builder
    public RoundSaveRequest(Long showId, LocalDateTime roundStartDateTime) {
        this.showId = showId;
        this.roundStartDateTime = roundStartDateTime;
    }

    public Round toEntity(ShowResponse show) {
        return Round.builder()
                .showId(show.getShowId())
                .roundStartDateTime(roundStartDateTime)
                .roundEndDateTime(roundStartDateTime.plusMinutes(show.getRunningTime()))
                .build();
    }
}
