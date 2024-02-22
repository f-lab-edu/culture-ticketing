package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Round;
import com.culture.ticketing.show.domain.Show;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoundSaveRequest {

    private Long showId;
    private LocalDateTime roundStartDateTime;

    @Builder
    public RoundSaveRequest(Long showId, LocalDateTime roundStartDateTime) {
        this.showId = showId;
        this.roundStartDateTime = roundStartDateTime;
    }

    public Round toEntity(Show show) {
        return Round.builder()
                .showId(show.getShowId())
                .roundStartDateTime(roundStartDateTime)
                .roundEndDateTime(roundStartDateTime.plusMinutes(show.getRunningTime()))
                .build();
    }
}
