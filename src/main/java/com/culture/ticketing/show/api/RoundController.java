package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rounds")
public class RoundController {

    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @PostMapping
    public void postRound(@RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }

    @GetMapping
    public List<RoundResponse> getRoundsByShowId(@RequestParam("showId") Long showId) {

        return roundService.findRoundsByShowId(showId);
    }
}
