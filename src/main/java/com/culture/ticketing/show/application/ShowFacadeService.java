package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingShowSeatService;
import com.culture.ticketing.booking.application.dto.BookingShowSeatResponse;
import com.culture.ticketing.booking.application.dto.RoundsShowSeatCountsResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.RoundService;
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final RoundPerformerService roundPerformerService;
    private final ShowAreaGradeService showAreaGradeService;
    private final BookingShowSeatService bookingShowSeatService;
    private final ShowSeatService showSeatService;

    public ShowFacadeService(ShowService showService, RoundService roundService, RoundPerformerService roundPerformerService,
                             ShowAreaGradeService showAreaGradeService, BookingShowSeatService bookingShowSeatService, ShowSeatService showSeatService) {
        this.showService = showService;
        this.roundService = roundService;
        this.roundPerformerService = roundPerformerService;
        this.showAreaGradeService = showAreaGradeService;
        this.bookingShowSeatService = bookingShowSeatService;
        this.showSeatService = showSeatService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        ShowResponse show = showService.findShowById(showId);
        List<RoundResponse> rounds = roundService.findByShowId(showId);
        List<RoundWithPerformersResponse> roundsWitPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(showId, rounds);
        List<ShowAreaGradeResponse> showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);

        return new ShowDetailResponse(show, roundsWitPerformers, showAreaGrades);
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersAndShowAreaGradesResponse> findRoundsByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        List<RoundResponse> rounds = roundService.findByShowIdAndRoundStartDate(showId, roundStartDate);
        List<RoundWithPerformersResponse> roundsWitPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(showId, rounds);

        RoundsShowSeatCountsResponse roundsShowSeatCounts = bookingShowSeatService.findRoundsShowSeatCounts(showId, roundsWitPerformers.getRoundIds());
        List<BookingShowSeatResponse> bookingShowSeats = bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdIn()

        return roundsWitPerformers.getRoundWithPerformers().stream()
                .map(roundWithPerformers -> new RoundWithPerformersAndShowAreaGradesResponse(
                        roundWithPerformers,
                        roundsShowSeatCounts.getShowSeatCountsByRoundId(roundWithPerformers.getRoundId())
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShowSeatResponse> findShowSeatsByShowAreaIdAndRoundId(Long showAreaId, Long roundId) {

        List<ShowSeatResponse> showSeats = showSeatService.findShowSeatsByShowAreaId(showAreaId);
        Set<Long> showSeatIds = getShowSeatIds(showSeats, ShowSeatResponse::getShowSeatId);

        List<BookingShowSeatResponse> bookingShowSeats = bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds);
        Set<Long> showSeatIdsInBooking = getShowSeatIds(bookingShowSeats, BookingShowSeatResponse::getBookingShowSeatId);

        return showSeats.stream()
                .map(showSeat -> showSeat.getAvailabilityUpdatedShowSeatResponse(showSeatIdsInBooking.contains(showSeat.getShowSeatId())))
                .collect(Collectors.toList());
    }

    private <T, R> Set<R> getShowSeatIds(List<T> list, Function<T, R> function) {
        return list.stream()
                .map(function)
                .collect(Collectors.toSet());
    }
}
