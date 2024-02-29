package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingFacadeService;
import com.culture.ticketing.booking.application.dto.ShowFloorGradeWithCountMapByRoundIdResponse;
import com.culture.ticketing.booking.application.dto.ShowSeatGradeWithCountMapByRoundIdResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.domain.Place;
import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowSeatsResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.domain.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundPerformerService roundPerformerService;
    private final ShowAreaGradeService showAreaGradeService;
    private final PlaceService placeService;
    private final BookingFacadeService bookingFacadeService;

    public ShowFacadeService(ShowService showService, RoundPerformerService roundPerformerService, ShowAreaGradeService showAreaGradeService,
                             PlaceService placeService, BookingFacadeService bookingFacadeService) {
        this.showService = showService;
        this.roundPerformerService = roundPerformerService;
        this.showAreaGradeService = showAreaGradeService;
        this.placeService = placeService;
        this.bookingFacadeService = bookingFacadeService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<RoundWithPerformersResponse> rounds = roundPerformerService.findRoundsWitPerformersByShowId(showId);
        List<ShowAreaGradeResponse> showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);

        return ShowDetailResponse.from(show, place, rounds, showAreaGrades);
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersAndShowSeatsResponse> findRoundsByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        List<RoundWithPerformersResponse> roundsWithPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRoundStartDate(showId, roundStartDate);
        List<Long> roundIds = roundsWithPerformers.stream()
                .map(RoundWithPerformersResponse::getRoundId)
                .collect(Collectors.toList());

        ShowSeatGradeWithCountMapByRoundIdResponse showSeatGradeWithAvailableCountMapByRoundId = bookingFacadeService.findShowSeatGradeWithAvailableCountMapByRoundId(showId, roundIds);
        ShowFloorGradeWithCountMapByRoundIdResponse showFloorGradeWithAvailableCountMapByRoundId = bookingFacadeService.findShowFloorGradeWithAvailableCountMapByRoundId(showId, roundIds);

        return roundsWithPerformers.stream()
                .map(roundWithPerformers -> new RoundWithPerformersAndShowSeatsResponse(
                        roundWithPerformers,
                        showSeatGradeWithAvailableCountMapByRoundId.getByRoundId(roundWithPerformers.getRoundId()),
                        showFloorGradeWithAvailableCountMapByRoundId.getByRoundId(roundWithPerformers.getRoundId())
                ))
                .collect(Collectors.toList());
    }
}
