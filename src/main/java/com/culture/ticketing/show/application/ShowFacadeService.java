package com.culture.ticketing.show.application;

import com.culture.ticketing.booking.application.BookingFacadeService;
import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.PerformerResponse;
import com.culture.ticketing.show.application.dto.RoundWithPerformersAndShowSeatsResponse;
import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeWithCountResponse;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final RoundPerformerService roundPerformerService;
    private final ShowSeatGradeService showSeatGradeService;
    private final PlaceService placeService;
    private final BookingFacadeService bookingFacadeService;

    public ShowFacadeService(ShowService showService, RoundService roundService, RoundPerformerService roundPerformerService,
                             PlaceService placeService, ShowSeatGradeService showSeatGradeService, BookingFacadeService bookingFacadeService) {
        this.showService = showService;
        this.roundService = roundService;
        this.roundPerformerService = roundPerformerService;
        this.showSeatGradeService = showSeatGradeService;
        this.placeService = placeService;
        this.bookingFacadeService = bookingFacadeService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<RoundWithPerformersResponse> rounds = findRoundsByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);

        return ShowDetailResponse.from(show, place, rounds, showSeatGrades);
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersResponse> findRoundsByShowId(Long showId) {

        List<Round> rounds = roundService.findByShowId(showId);
        List<Long> roundIds = rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());

        Map<Long, List<PerformerResponse>> performersMapByRoundId = roundPerformerService.findPerformersMapByRoundId(showId, roundIds);

        return rounds.stream()
                .map(round -> new RoundWithPerformersResponse(round, performersMapByRoundId.getOrDefault(round.getRoundId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersAndShowSeatsResponse> findRoundsByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {
        List<Round> rounds = roundService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);
        System.out.println(roundStartDate + "***");
        List<Long> roundIds = rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());

        Map<Long, List<PerformerResponse>> performersMapByRoundId = roundPerformerService.findPerformersMapByRoundId(showId, roundIds);

        Map<Long, Map<Long, Long>> showSeatAvailableCountMapByShowSeatGradeIdAndRoundId = bookingFacadeService.findShowSeatAvailableCountMapByShowSeatGradeIdAndRoundId(showId, roundIds);

        return rounds.stream()
                .map(round -> new RoundWithPerformersAndShowSeatsResponse(
                        round,
                        performersMapByRoundId.get(round.getRoundId()),
                        ShowSeatGradeWithCountResponse.from(showSeatAvailableCountMapByShowSeatGradeIdAndRoundId.get(round.getRoundId()))
                ))
                .collect(Collectors.toList());
    }

}
