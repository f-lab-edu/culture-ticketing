package com.culture.ticketing.show.round_performer.api;

import com.culture.ticketing.show.round_performer.application.RoundService;
import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void postRound(@RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }

    @GetMapping
    public List<RoundResponse> getRoundsByShowId(@RequestParam("showId") Long showId) {

        return roundService.findRoundResponsesByShowId(showId);
    }

    @GetMapping("/detail")
    public List<RoundWithPerformersAndShowAreaGradesResponse> getRoundsByShowIdAndRoundStartDate(@RequestParam("showId") Long showId,
                                                                                                 @RequestParam("roundStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate roundStartDate) {

        return showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);
    }
}
