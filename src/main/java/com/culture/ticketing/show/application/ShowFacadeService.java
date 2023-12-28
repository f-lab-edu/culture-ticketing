package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowFacadeService(ShowService showService, RoundService roundService, ShowSeatGradeService showSeatGradeService) {
        this.showService = showService;
        this.roundService = roundService;
        this.showSeatGradeService = showSeatGradeService;
    }

    public ShowDetailResponse findShowById(Long showId) {

        ShowResponse showDetail = showService.findShowDetailById(showId);
        List<RoundResponse> rounds = roundService.findRoundsByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);

        return new ShowDetailResponse(showDetail, rounds, showSeatGrades);
    }
}
