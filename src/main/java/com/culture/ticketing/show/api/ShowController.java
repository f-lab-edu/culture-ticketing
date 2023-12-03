package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.PerformerService;
import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.PerformerResponse;
import com.culture.ticketing.show.application.dto.PerformerSaveRequest;
import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final RoundService roundService;
    private final PerformerService performerService;

    public ShowController(ShowService showService, RoundService roundService, PerformerService performerService) {
        this.showService = showService;
        this.roundService = roundService;
        this.performerService = performerService;
    }

    @PostMapping("")
    public void postShow(@Valid @RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @PostMapping("/rounds")
    public void postRound(@Valid @RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }

    @PostMapping("/performers")
    public void postPerformer(@Valid @RequestBody PerformerSaveRequest request) {

        performerService.createPerformer(request);
    }

    @GetMapping("/{showId}/performers")
    public List<PerformerResponse> getPerformers(@PathVariable(value = "showId") Long showId) {

        return performerService.findPerformers(showId);
    }
}
