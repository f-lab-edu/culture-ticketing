package com.culture.ticketing.show.round_performer.api;

import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.dto.RoundPerformersSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/round-performers")
public class RoundPerformerController {

    private final RoundPerformerService roundPerformerService;

    public RoundPerformerController(RoundPerformerService roundPerformerService) {
        this.roundPerformerService = roundPerformerService;
    }

    @PostMapping
    public void postRoundPerformers(@RequestBody RoundPerformersSaveRequest request) {

        roundPerformerService.createRoundPerformers(request);
    }
}