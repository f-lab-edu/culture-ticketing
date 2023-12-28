package com.culture.ticketing.show.application;

import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.DuplicatedRoundDateTimeException;
import com.culture.ticketing.show.exception.OutOfRangeRoundDateTime;
import com.culture.ticketing.show.exception.RoundNotFoundException;
import com.culture.ticketing.show.infra.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class RoundService {

    private final RoundRepository roundRepository;

    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Transactional(readOnly = true)
    public Round findById(Long roundId) {
        return roundRepository.findById(roundId).orElseThrow(() -> {
            throw new RoundNotFoundException(roundId);
        });
    }

    @Transactional
    public void createRound(Show show, Round round) {

        checkOutOfRangeRoundDateTime(round, show);
        checkDuplicatedRoundDateTime(round);

        roundRepository.save(round);
    }

    private void checkDuplicatedRoundDateTime(Round round) {
        roundRepository.findByShowIdAndDuplicatedRoundDateTime(
                round.getShowId(),
                round.getRoundStartDateTime(),
                round.getRoundEndDateTime()).ifPresent(schedule -> {
            throw new DuplicatedRoundDateTimeException();
        });
    }

    private void checkOutOfRangeRoundDateTime(Round round, Show show) {
        LocalDateTime showStartDateTime = LocalDateTime.of(show.getShowStartDate(), LocalTime.MIN);
        LocalDateTime showEndDateTime = LocalDateTime.of(show.getShowEndDate(), LocalTime.MAX);
        if (round.getRoundStartDateTime().isBefore(showStartDateTime)
                || round.getRoundEndDateTime().isAfter(showEndDateTime)) {
            throw new OutOfRangeRoundDateTime();
        }
    }

    @Transactional(readOnly = true)
    public List<Round> findByShowId(Long showId) {

        return roundRepository.findByShowId(showId);
    }
}
