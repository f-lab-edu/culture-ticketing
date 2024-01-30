package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.application.dto.RoundWithPerformersAndShowSeatsResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rounds")
public class RoundController {

    private final RoundService roundService;
    private final ShowFacadeService showFacadeService;

    public RoundController(RoundService roundService, ShowFacadeService showFacadeService) {
        this.roundService = roundService;
        this.showFacadeService = showFacadeService;
    }

    @PostMapping
    public void postRound(@Valid @RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }

    @GetMapping
    public List<RoundResponse> getRoundsByShowId(@RequestParam("showId") Long showId) {

        return roundService.findRoundsByShowId(showId);
    }

    @GetMapping("/detail")
    public List<RoundWithPerformersAndShowSeatsResponse> getRoundsByShowIdAndRoundStartDate(@RequestParam("showId") Long showId,
                                                                                            @RequestParam("roundStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate roundStartDate) {

        return showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);
    }
}
