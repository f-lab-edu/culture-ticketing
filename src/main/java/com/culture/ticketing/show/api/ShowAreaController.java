package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowAreaService;
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/show-areas")
public class ShowAreaController {

    private final ShowAreaService showAreaService;

    public ShowAreaController(ShowAreaService showAreaService) {
        this.showAreaService = showAreaService;
    }

    @PostMapping
    public void postShowArea(@Valid @RequestBody ShowAreaSaveRequest request) {

        showAreaService.createShowArea(request);
    }
}
