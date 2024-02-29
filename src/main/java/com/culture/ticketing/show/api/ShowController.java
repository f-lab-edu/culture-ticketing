package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.domain.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowFacadeService showFacadeService;

    public ShowController(ShowService showService, ShowFacadeService showFacadeService) {
        this.showService = showService;
        this.showFacadeService = showFacadeService;
    }

    @PostMapping
    public void postShow(@RequestBody ShowSaveRequest request) {

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

        return showFacadeService.findShowById(showId);
    }

}
