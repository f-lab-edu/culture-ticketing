package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @ResponseBody
    @PostMapping("")
    public void postPlace(@Valid @RequestBody PlaceSaveRequest request) {

        placeService.createPlace(request);
    }
}
