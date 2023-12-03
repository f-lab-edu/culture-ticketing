package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.DuplicatedRoundDateTimeException;
import com.culture.ticketing.show.exception.OutOfRangeRoundDateTime;
import com.culture.ticketing.show.infra.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.*;

@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final ShowService showService;

    public RoundService(RoundRepository roundRepository, ShowService showService) {
        this.roundRepository = roundRepository;
        this.showService = showService;
    }

    @Transactional
    public void createRound(RoundSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(request.getRoundStartDateTime(), EMPTY_ROUND_DATE_TIME.getMessage());

        Show show = showService.findShowById(request.getShowId());
        Round round = request.toEntity(show);
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
        if (round.getRoundStartDateTime().isAfter(showStartDateTime)
                && round.getRoundEndDateTime().isBefore(showEndDateTime)) {
            throw new OutOfRangeRoundDateTime();
        }

    }
}
