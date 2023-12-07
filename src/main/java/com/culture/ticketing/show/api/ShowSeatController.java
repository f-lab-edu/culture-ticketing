package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @PostMapping("")
    public void postShowSeat(@Valid @RequestBody ShowSeatSaveRequest request) {

        showSeatService.createShowSeat(request);
    }
}
