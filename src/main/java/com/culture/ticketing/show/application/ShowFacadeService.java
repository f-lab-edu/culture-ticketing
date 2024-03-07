package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingShowSeatService;
import com.culture.ticketing.booking.application.dto.BookingShowSeatResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.application.dto.ShowAreaResponse;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatCountResponse;
import com.culture.ticketing.show.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundPerformerService roundPerformerService;
    private final ShowAreaGradeService showAreaGradeService;
    private final ShowAreaService showAreaService;
    private final BookingShowSeatService bookingShowSeatService;
    private final ShowSeatService showSeatService;

    public ShowFacadeService(ShowService showService, RoundPerformerService roundPerformerService, ShowAreaGradeService showAreaGradeService,
                             ShowAreaService showAreaService, BookingShowSeatService bookingShowSeatService, ShowSeatService showSeatService) {
        this.showService = showService;
        this.roundPerformerService = roundPerformerService;
        this.showAreaGradeService = showAreaGradeService;
        this.showAreaService = showAreaService;
        this.bookingShowSeatService = bookingShowSeatService;
        this.showSeatService = showSeatService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        ShowResponse show = showService.findShowById(showId);
        List<RoundWithPerformersResponse> roundsWitPerformers = roundPerformerService.findRoundsWitPerformersByShowId(showId);
        List<ShowAreaGradeResponse> showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);

        return new ShowDetailResponse(show, roundsWitPerformers, showAreaGrades);
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersAndShowAreaGradesResponse> findRoundsByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        List<RoundWithPerformersResponse> roundsWithPerformers = roundPerformerService.findRoundsWithPerformersByShowIdAndRoundStartDate(showId, roundStartDate);

        List<ShowAreaResponse> showAreas = showAreaService.findShowAreasByShowId(showId);
        Map<Long, ShowAreaResponse> showAreaMapById = getShowAreaMapById(showAreas);

        Set<Long> showAreaIds = getIds(showAreas, ShowAreaResponse::getShowAreaId);
        List<ShowSeatResponse> showSeats = showSeatService.findByShowAreaIds(showAreaIds);
        Map<Long, Long> showSeatCountMapByShowAreaGradeId = getShowSeatCountMapByShowAreaGradeId(showAreaMapById, showSeats);
        List<ShowSeatCountResponse> showSeatCounts = getShowSeatCountResponses(showId, showSeatCountMapByShowAreaGradeId);

        Set<Long> roundIds = getIds(roundsWithPerformers, RoundWithPerformersResponse::getRoundId);
        Map<Long, List<ShowSeatResponse>> bookingShowSeatResponsesMapByRoundId = getBookingShowSeatResponsesMapByRoundId(roundIds);

        return roundsWithPerformers.stream()
                .map(roundWithPerformers -> new RoundWithPerformersAndShowAreaGradesResponse(
                        roundWithPerformers,
                        getSubtractedBookingShowSeatCountResponses(
                                showSeatCounts,
                                showAreaMapById,
                                bookingShowSeatResponsesMapByRoundId.get(roundWithPerformers.getRoundId())
                        )
                ))
                .collect(Collectors.toList());
    }

    private List<ShowSeatCountResponse> getShowSeatCountResponses(Long showId, Map<Long, Long> showSeatCountMapByShowAreaGradeId) {

        List<ShowAreaGradeResponse> showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);

        return showAreaGrades.stream()
                .map(showAreaGrade -> new ShowSeatCountResponse(showAreaGrade, showSeatCountMapByShowAreaGradeId.getOrDefault(showAreaGrade.getShowAreaGradeId(), 0L)))
                .collect(Collectors.toList());
    }

    private Map<Long, List<ShowSeatResponse>> getBookingShowSeatResponsesMapByRoundId(Collection<Long> roundIds) {

        List<BookingShowSeatResponse> bookingShowSeats = bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdIn(roundIds);
        Set<Long> showSeatIdsInBooking = getIds(bookingShowSeats, BookingShowSeatResponse::getShowSeatId);

        List<ShowSeatResponse> showSeatsInBooking = showSeatService.findByIds(showSeatIdsInBooking);

        Map<Long, ShowSeatResponse> showSeatMapById = showSeatsInBooking.stream()
                .collect(Collectors.toMap(ShowSeatResponse::getShowSeatId, Function.identity()));

        return bookingShowSeats.stream()
                .collect(Collectors.groupingBy(BookingShowSeatResponse::getRoundId,
                        Collectors.mapping(bookingShowSeat -> showSeatMapById.get(bookingShowSeat.getShowSeatId()), Collectors.toList())));
    }

    private List<ShowSeatCountResponse> getSubtractedBookingShowSeatCountResponses(List<ShowSeatCountResponse> showSeatCounts,
                                                                                   Map<Long, ShowAreaResponse> showAreaMapById,
                                                                                   List<ShowSeatResponse> bookingShowSeats) {

        Map<Long, Long> bookingShowSeatCountMapByShowAreaGradeId = getShowSeatCountMapByShowAreaGradeId(showAreaMapById, bookingShowSeats);

        return showSeatCounts.stream()
                .map(showSeatCount -> showSeatCount.getSubtractedShowSeatCountResponse(
                        bookingShowSeatCountMapByShowAreaGradeId.get(showSeatCount.getShowAreaGradeId())
                ))
                .collect(Collectors.toList());
    }

    private Map<Long, ShowAreaResponse> getShowAreaMapById(List<ShowAreaResponse> showAreas) {
        return showAreas.stream()
                .collect(Collectors.toMap(ShowAreaResponse::getShowAreaId, Function.identity()));
    }

    private Map<Long, Long> getShowSeatCountMapByShowAreaGradeId(Map<Long, ShowAreaResponse> showAreaMapById, List<ShowSeatResponse> showSeats) {

        return showSeats.stream()
                .collect(Collectors.groupingBy(showSeat -> showAreaMapById.get(showSeat.getShowAreaId()).getShowAreaGradeId(), Collectors.counting()));
    }

    @Transactional(readOnly = true)
    public List<ShowSeatResponse> findShowSeatsByShowAreaIdAndRoundId(Long showAreaId, Long roundId) {

        List<ShowSeatResponse> showSeats = showSeatService.findShowSeatsByShowAreaId(showAreaId);
        Set<Long> showSeatIds = getIds(showSeats, ShowSeatResponse::getShowSeatId);

        List<BookingShowSeatResponse> bookingShowSeats = bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds);
        Set<Long> showSeatIdsInBooking = getIds(bookingShowSeats, BookingShowSeatResponse::getShowSeatId);

        return showSeats.stream()
                .map(showSeat -> showSeat.getAvailabilityUpdatedShowSeatResponse(!showSeatIdsInBooking.contains(showSeat.getShowSeatId())))
                .collect(Collectors.toList());
    }

    private <T, R> Set<R> getIds(List<T> list, Function<T, R> function) {
        return list.stream()
                .map(function)
                .collect(Collectors.toSet());
    }
}
