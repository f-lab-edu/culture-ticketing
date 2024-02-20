package com.culture.ticketing.show.round_performer.api;

import com.culture.ticketing.show.round_performer.application.PerformerService;
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse;
import com.culture.ticketing.show.round_performer.application.dto.PerformerSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/performers")
public class PerformerController {

    private final PerformerService performerService;

    public PerformerController(PerformerService performerService) {
        this.performerService = performerService;
    }

    @PostMapping
    public void postPerformer(@RequestBody PerformerSaveRequest request) {

        performerService.createPerformer(request);
    }

    @GetMapping
    public List<PerformerResponse> getPerformersByShowId(@RequestParam(value = "showId") Long showId) {

        return performerService.findPerformersByShowId(showId);
    }

}