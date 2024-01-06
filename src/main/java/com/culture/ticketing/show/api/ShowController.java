package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.domain.Category;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowFacadeService showFacadeService;
    private final RoundService roundService;

    public ShowController(ShowService showService, ShowFacadeService showFacadeService, RoundService roundService) {
        this.showService = showService;
        this.showFacadeService = showFacadeService;
        this.roundService = roundService;
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

    @GetMapping("/{showId}/calendar")
    public List<LocalDate> getShowRoundExistDates(@PathVariable("showId") Long showId) {

        return roundService.findRoundExistDatesByShowId(showId);
    }

    @GetMapping("/{showId}")
    public ShowDetailResponse getShowById(@PathVariable("showId") Long showId) {

        return showFacadeService.findShowById(showId);
    }
}
