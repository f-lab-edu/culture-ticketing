package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final RoundService roundService;

    public ShowController(ShowService showService, RoundService roundService) {
        this.showService = showService;
        this.roundService = roundService;
    }

    @PostMapping("")
    public void postShow(@Valid @RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @PostMapping("/rounds")
    public void postRound(@Valid @RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }
}
