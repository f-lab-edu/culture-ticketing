package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public void postPlaceSeat(@Valid @RequestBody PlaceSeatSaveRequest request) {

        seatService.createPlaceSeat(request);
    }
}
