package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.dto.RoundWithPerformersSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/rounds")
public class RoundController {

    private final ShowFacadeService showFacadeService;

    public RoundController(ShowFacadeService showFacadeService) {
        this.showFacadeService = showFacadeService;
    }

    @PostMapping
    public void postRound(@Valid @RequestBody RoundWithPerformersSaveRequest request) {

        showFacadeService.createRoundWithPerformers(request);
    }
}
