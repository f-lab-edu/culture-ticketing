package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public void postPlace(@Valid @RequestBody PlaceSaveRequest request) {

        placeService.createPlace(request);
    }

    @GetMapping
    public List<PlaceResponse> getPlaces(@RequestParam(name = "offset") Long offset,
                                         @RequestParam(name = "size") int size) {

        return placeService.findPlaces(offset, size);
    }

}
