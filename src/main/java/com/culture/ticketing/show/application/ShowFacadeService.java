package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingFacadeService;
import com.culture.ticketing.place.application.AreaService;
import com.culture.ticketing.booking.application.dto.ShowFloorGradeWithCountMapByRoundIdResponse;
import com.culture.ticketing.booking.application.dto.ShowSeatGradeWithCountMapByRoundIdResponse;
import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Area;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.ShowPlaceAreaResponse;
import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowSeatsResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService;
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundPerformerService roundPerformerService;
    private final ShowSeatGradeService showSeatGradeService;
    private final ShowFloorGradeService showFloorGradeService;
    private final PlaceService placeService;
    private final BookingFacadeService bookingFacadeService;
    private final AreaService areaService;

    public ShowFacadeService(ShowService showService, RoundPerformerService roundPerformerService,
                             ShowSeatGradeService showSeatGradeService, ShowFloorGradeService showFloorGradeService,
                             PlaceService placeService, BookingFacadeService bookingFacadeService, AreaService areaService) {
        this.showService = showService;
        this.roundPerformerService = roundPerformerService;
        this.showSeatGradeService = showSeatGradeService;
        this.showFloorGradeService = showFloorGradeService;
        this.placeService = placeService;
        this.bookingFacadeService = bookingFacadeService;
        this.areaService = areaService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<RoundWithPerformersResponse> rounds = roundPerformerService.findRoundsWitPerformersByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);
        List<ShowFloorGradeResponse> showFloorGrades = showFloorGradeService.findShowFloorGradesByShowId(showId);

        return ShowDetailResponse.from(show, place, rounds, showSeatGrades, showFloorGrades);
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

    @Transactional(readOnly = true)
    public List<ShowPlaceAreaResponse> findAreasByShowId(Long showId) {
        Show show = showService.findShowById(showId);
        List<Area> areas = areaService.findByShowId(show.getShowId());

        return areas.stream()
                .map(ShowPlaceAreaResponse::from)
                .collect(Collectors.toList());
    }
}
