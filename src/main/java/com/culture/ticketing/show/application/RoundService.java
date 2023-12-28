package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.DuplicatedRoundDateTimeException;
import com.culture.ticketing.show.exception.OutOfRangeRoundDateTimeException;
import com.culture.ticketing.show.exception.ShowPerformerNotMatchException;
import com.culture.ticketing.show.infra.PerformerRepository;
import com.culture.ticketing.show.infra.RoundPerformerRepository;
import com.culture.ticketing.show.infra.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final RoundPerformerRepository roundPerformerRepository;
    private final PerformerRepository performerRepository;
    private final ShowService showService;

    public RoundService(RoundRepository roundRepository, RoundPerformerRepository roundPerformerRepository,
                        PerformerRepository performerRepository, ShowService showService) {
        this.roundRepository = roundRepository;
        this.roundPerformerRepository = roundPerformerRepository;
        this.performerRepository = performerRepository;
        this.showService = showService;
    }

    @Transactional
    public void createRound(RoundSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getRoundStartDateTime(), "회차 시작 일시를 입력해주세요.");

        Show show = showService.findShowById(request.getShowId());
        Round round = request.toEntity(show);
        checkOutOfRangeRoundDateTime(round, show);
        checkDuplicatedRoundDateTime(round);
        Round saveRound = roundRepository.save(round);

        checkShowPerformerMatch(round.getShowId(), request.getPerformerIds());
        List<RoundPerformer> roundPerformers = request.getPerformerIds().stream()
                .map(performerId -> RoundPerformer.builder()
                        .roundId(saveRound.getRoundId())
                        .performerId(performerId)
                        .build())
                .collect(Collectors.toList());
        roundPerformerRepository.saveAll(roundPerformers);
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
            throw new OutOfRangeRoundDateTimeException();
        }
    }

    private void checkShowPerformerMatch(Long showId, Set<Long> performerIds) {

        List<Performer> foundPerformers = performerRepository.findByShowIdAndPerformerIdIn(showId, performerIds);
        if (foundPerformers.size() != performerIds.size()) {
            String notMatchingPerformerIds = performerIds.stream()
                    .filter(performerId -> foundPerformers.stream().noneMatch(performer -> performer.getPerformerId().equals(performerId)))
                    .map(Objects::toString)
                    .collect(Collectors.joining(","));
            throw new ShowPerformerNotMatchException(notMatchingPerformerIds);
        }
    }
}
