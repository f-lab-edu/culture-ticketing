package com.culture.ticketing.place.api;

import com.culture.ticketing.common.response.BaseResponse;
import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.culture.ticketing.common.response.BaseResponse.success;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping("")
    public BaseResponse<Void> postPlace(@Valid @RequestBody PlaceSaveRequest request) {

        placeService.createPlace(request);

        return success();
    }

    @GetMapping("")
    public BaseResponse<Page<PlaceResponse>> getPlaces(@RequestParam(name = "page") int page,
                                                       @RequestParam(name = "size") int size) {

        Page<PlaceResponse> data = placeService.getPlaces(page, size).map(PlaceResponse::new);

        return success(data);
    }

}
