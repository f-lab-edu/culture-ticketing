package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.RoundPerformerService;
import com.culture.ticketing.show.application.dto.RoundPerformersSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/round-performers")
public class RoundPerformerController {

    private final RoundPerformerService roundPerformerService;

    public RoundPerformerController(RoundPerformerService roundPerformerService) {
        this.roundPerformerService = roundPerformerService;
    }

    @PostMapping
    public void postRoundPerformers(@Valid @RequestBody RoundPerformersSaveRequest request) {

        roundPerformerService.createRoundPerformers(request);
    }
}
