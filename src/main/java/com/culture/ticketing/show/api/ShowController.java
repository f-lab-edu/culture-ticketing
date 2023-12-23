package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.ShowSeatGradeService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.Category;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;
    private final RoundService roundService;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowController(ShowService showService, RoundService roundService, ShowSeatGradeService showSeatGradeService) {
        this.showService = showService;
        this.roundService = roundService;
        this.showSeatGradeService = showSeatGradeService;
    }

    @PostMapping
    public void postShow(@Valid @RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @GetMapping
    public List<ShowResponse> getShows(@RequestParam(name = "offset") Long offset,
                                       @RequestParam(name = "size") int size,
                                       @RequestParam(name = "category", required = false) Category category) {

        return showService.findShows(offset, size, category);
    }

    @GetMapping("/{showId}")
    public ShowDetailResponse getShowById(@PathVariable("showId") Long showId) {

        ShowResponse showDetail = showService.findShowDetailById(showId);
        List<RoundResponse> rounds = roundService.findRoundsByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);

        return new ShowDetailResponse(showDetail, rounds, showSeatGrades);
    }
}
