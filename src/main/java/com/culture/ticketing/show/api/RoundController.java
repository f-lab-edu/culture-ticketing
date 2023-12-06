package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rounds")
public class RoundController {

    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @PostMapping("")
    public void postRound(@Valid @RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }
}
