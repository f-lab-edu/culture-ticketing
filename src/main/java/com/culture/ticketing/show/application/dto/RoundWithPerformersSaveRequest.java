package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
public class RoundWithPerformersSaveRequest {

    @NotNull
    private Long showId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime roundStartDateTime;
    private Set<Long> performerIds;

    public Round toEntity(Show show) {
        return Round.builder()
                .showId(show.getShowId())
                .roundStartDateTime(roundStartDateTime)
                .roundEndDateTime(roundStartDateTime.plusMinutes(show.getRunningTime()))
                .build();
    }
}
