package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingShowSeatService;
import com.culture.ticketing.booking.application.dto.RoundsShowSeatCountsResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse;
import com.culture.ticketing.show.domain.Place;
import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.RoundService;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse;
import com.culture.ticketing.show.round_performer.domain.Round;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final RoundPerformerService roundPerformerService;
    private final ShowAreaGradeService showAreaGradeService;
    private final PlaceService placeService;
    private final BookingShowSeatService bookingShowSeatService;

    public ShowFacadeService(ShowService showService, RoundService roundService, RoundPerformerService roundPerformerService,
                             ShowAreaGradeService showAreaGradeService, PlaceService placeService, BookingShowSeatService bookingShowSeatService) {
        this.showService = showService;
        this.roundService = roundService;
        this.roundPerformerService = roundPerformerService;
        this.showAreaGradeService = showAreaGradeService;
        this.placeService = placeService;
        this.bookingShowSeatService = bookingShowSeatService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<Round> rounds = roundService.findByShowId(showId);
        RoundsWithPerformersResponse roundsWitPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(showId, rounds);
        ShowAreaGradesResponse showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);

        return ShowDetailResponse.from(show, place, roundsWitPerformers, showAreaGrades);
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersAndShowAreaGradesResponse> findRoundsByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        List<Round> rounds = roundService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);
        RoundsWithPerformersResponse roundsWitPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(showId, rounds);

        RoundsShowSeatCountsResponse roundsShowSeatCounts = bookingShowSeatService.findRoundsShowSeatCounts(showId, roundsWitPerformers.getRoundIds());

        return roundsWitPerformers.getRoundWithPerformers().stream()
                .map(roundWithPerformers -> new RoundWithPerformersAndShowAreaGradesResponse(
                        roundWithPerformers,
                        roundsShowSeatCounts.getShowSeatCountsByRoundId(roundWithPerformers.getRoundId())
                ))
                .collect(Collectors.toList());
    }
}
