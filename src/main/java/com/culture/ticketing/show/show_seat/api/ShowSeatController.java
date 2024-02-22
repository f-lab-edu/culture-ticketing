package com.culture.ticketing.show.show_seat.api;

import com.culture.ticketing.show.show_seat.application.ShowSeatService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @PostMapping
    public void postShowSeat(@RequestBody ShowSeatSaveRequest request) {

        showSeatService.createShowSeat(request);
    }
}
