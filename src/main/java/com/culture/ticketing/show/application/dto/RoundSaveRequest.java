package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoundSaveRequest {

    @NotNull
    private Long showId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
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
