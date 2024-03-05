package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.PlaceService;
import com.culture.ticketing.show.application.dto.PlaceSaveRequest;
import com.culture.ticketing.show.application.dto.PlacesResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public void postPlace(@RequestBody PlaceSaveRequest request) {

        placeService.createPlace(request);
    }

    @GetMapping
    public PlacesResponse getPlaces(@RequestParam(name = "offset") Long offset,
                                    @RequestParam(name = "size") int size) {

        return placeService.findPlaces(offset, size);
    }

}
