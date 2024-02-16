package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingFacadeService;
import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.RoundWithPerformersAndShowSeatsResponse;
import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeWithCountResponse;
import com.culture.ticketing.show.domain.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundPerformerService roundPerformerService;
    private final ShowSeatGradeService showSeatGradeService;
    private final PlaceService placeService;
    private final BookingFacadeService bookingFacadeService;

    public ShowFacadeService(ShowService showService, RoundPerformerService roundPerformerService, ShowSeatGradeService showSeatGradeService,
                             PlaceService placeService, BookingFacadeService bookingFacadeService) {
        this.showService = showService;
        this.roundPerformerService = roundPerformerService;
        this.showSeatGradeService = showSeatGradeService;
        this.placeService = placeService;
        this.bookingFacadeService = bookingFacadeService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<RoundWithPerformersResponse> rounds = roundPerformerService.findRoundsWitPerformersByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);

        return ShowDetailResponse.from(show, place, rounds, showSeatGrades);
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersAndShowSeatsResponse> findRoundsByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        List<RoundWithPerformersResponse> roundsWithPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRoundStartDate(showId, roundStartDate);
        List<Long> roundIds = roundsWithPerformers.stream()
                .map(RoundWithPerformersResponse::getRoundId)
                .collect(Collectors.toList());

        Map<Long, Map<ShowSeatGradeResponse, Long>> showSeatAvailableCountMapByShowSeatGradeAndRoundId = bookingFacadeService.findShowSeatAvailableCountMapByShowSeatGradeAndRoundId(showId, roundIds);

        return roundsWithPerformers.stream()
                .map(roundWithPerformers -> new RoundWithPerformersAndShowSeatsResponse(
                        roundWithPerformers,
                        ShowSeatGradeWithCountResponse.from(showSeatAvailableCountMapByShowSeatGradeAndRoundId.get(roundWithPerformers.getRoundId()))
                ))
                .collect(Collectors.toList());
    }
}
