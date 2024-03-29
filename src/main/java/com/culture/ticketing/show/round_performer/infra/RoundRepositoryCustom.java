package com.culture.ticketing.show.round_performer.infra;

import com.culture.ticketing.show.round_performer.domain.Round;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoundRepositoryCustom {

    Optional<Round> findByShowIdAndDuplicatedRoundDateTime(Long showId, LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime);

    List<Round> findByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate);
}
