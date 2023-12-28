package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.RoundWithPerformersSaveRequest;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_ROUND_DATE_TIME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final RoundPerformerService roundPerformerService;
    private final ShowSeatGradeService showSeatGradeService;
    private final PlaceService placeService;

    public ShowFacadeService(ShowService showService, RoundService roundService, RoundPerformerService roundPerformerService,
                             ShowSeatGradeService showSeatGradeService, PlaceService placeService) {
        this.showService = showService;
        this.roundService = roundService;
        this.roundPerformerService = roundPerformerService;
        this.showSeatGradeService = showSeatGradeService;
        this.placeService = placeService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        ShowResponse showDetail = ShowResponse.from(show, place);
        List<RoundResponse> rounds = roundService.findRoundsByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);

        return new ShowDetailResponse(showDetail, rounds, showSeatGrades);
    }

    @Transactional
    public void createRoundWithPerformers(RoundWithPerformersSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(request.getRoundStartDateTime(), EMPTY_ROUND_DATE_TIME.getMessage());

        Show show = showService.findShowById(request.getShowId());

        Round round = request.toEntity(show);
        roundService.createRound(show, round);

        roundPerformerService.createRoundPerformers(round.getRoundId(), request.getPerformerIds());
    }
}
