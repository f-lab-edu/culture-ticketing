package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;
    private final SeatService seatService;

    public PlaceController(PlaceService placeService, SeatService seatService) {
        this.placeService = placeService;
        this.seatService = seatService;
    }

    @PostMapping("")
    public void postPlace(@Valid @RequestBody PlaceSaveRequest request) {

        placeService.createPlace(request);
    }

    @GetMapping("")
    public List<PlaceResponse> getPlaces(@RequestParam(name = "lastPlaceId") Long lastPlaceId,
                                         @RequestParam(name = "size") int size) {

        return placeService.getPlaces(lastPlaceId, size);
    }

    @PostMapping("/seats")
    public void postPlaceSeat(@Valid @RequestBody PlaceSeatSaveRequest request) {

        seatService.createPlaceSeat(request);
    }
}
