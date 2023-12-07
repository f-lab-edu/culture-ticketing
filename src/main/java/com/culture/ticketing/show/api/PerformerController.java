package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.PerformerService;
import com.culture.ticketing.show.application.dto.PerformerResponse;
import com.culture.ticketing.show.application.dto.PerformerSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/performers")
public class PerformerController {

    private final PerformerService performerService;

    public PerformerController(PerformerService performerService) {
        this.performerService = performerService;
    }

    @PostMapping("")
    public void postPerformer(@Valid @RequestBody PerformerSaveRequest request) {

        performerService.createPerformer(request);
    }

    @GetMapping("")
    public List<PerformerResponse> getPerformersByShowId(@RequestParam(value = "showId") Long showId) {

        return performerService.findPerformersByShowId(showId);
    }

}
