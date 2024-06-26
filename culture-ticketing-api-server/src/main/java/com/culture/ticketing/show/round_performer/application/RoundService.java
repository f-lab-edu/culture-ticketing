package com.culture.ticketing.show.round_performer.application;

import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.round_performer.domain.Round;
import com.culture.ticketing.show.round_performer.exception.DuplicatedRoundDateTimeException;
import com.culture.ticketing.show.round_performer.exception.OutOfRangeRoundDateTimeException;
import com.culture.ticketing.show.round_performer.exception.RoundNotFoundException;
import com.culture.ticketing.show.round_performer.infra.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final ShowService showService;

    public RoundService(RoundRepository roundRepository, ShowService showService) {
        this.roundRepository = roundRepository;
        this.showService = showService;
    }

    @Transactional(readOnly = true)
    public Round findById(Long roundId) {
        return roundRepository.findById(roundId).orElseThrow(() -> {
            throw new RoundNotFoundException(roundId);
        });
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long roundId) {
        return !roundRepository.existsById(roundId);
    }

    @Transactional
    public void createRound(RoundSaveRequest request) {

        checkValidRoundSaveRequest(request);

        ShowResponse show = showService.findShowById(request.getShowId());
        Round round = request.toEntity(show);

        checkOutOfRangeRoundDateTime(round, show);
        checkDuplicatedRoundDateTime(round);

        roundRepository.save(round);
    }

    private void checkValidRoundSaveRequest(RoundSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getRoundStartDateTime(), "시작 회차 일시를 입력해주세요.");
    }

    private void checkDuplicatedRoundDateTime(Round round) {
        roundRepository.findByShowIdAndDuplicatedRoundDateTime(
                round.getShowId(),
                round.getRoundStartDateTime(),
                round.getRoundEndDateTime()).ifPresent(schedule -> {
            throw new DuplicatedRoundDateTimeException();
        });
    }

    private void checkOutOfRangeRoundDateTime(Round round, ShowResponse show) {
        LocalDateTime showStartDateTime = LocalDateTime.of(show.getShowStartDate(), LocalTime.MIN);
        LocalDateTime showEndDateTime = LocalDateTime.of(show.getShowEndDate(), LocalTime.MAX);
        if (round.getRoundStartDateTime().isBefore(showStartDateTime)
                || round.getRoundEndDateTime().isAfter(showEndDateTime)) {
            throw new OutOfRangeRoundDateTimeException();
        }
    }

    @Transactional(readOnly = true)
    public List<RoundResponse> findByShowId(Long showId) {

        return getRoundResponses(roundRepository.findByShowId(showId));
    }

    @Transactional(readOnly = true)
    public List<RoundResponse> findByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        return getRoundResponses(roundRepository.findByShowIdAndRoundStartDate(showId, roundStartDate));
    }

    private List<RoundResponse> getRoundResponses(List<Round> rounds) {
        return rounds.stream()
                .map(RoundResponse::new)
                .collect(Collectors.toList());
    }
}
