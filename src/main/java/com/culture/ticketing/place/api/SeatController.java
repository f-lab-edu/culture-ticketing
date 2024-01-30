package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public void postPlaceSeat(@RequestBody PlaceSeatSaveRequest request) {

        seatService.createPlaceSeat(request);
    }
}
