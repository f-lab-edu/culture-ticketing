package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final ShowSeatGradeService showSeatGradeService;
    private final PlaceService placeService;

    public ShowFacadeService(ShowService showService, RoundService roundService, ShowSeatGradeService showSeatGradeService, PlaceService placeService) {
        this.showService = showService;
        this.roundService = roundService;
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
}
