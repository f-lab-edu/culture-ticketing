package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFloorService;
import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/show-floors")
public class ShowFloorController {

    private final ShowFloorService showFloorService;

    public ShowFloorController(ShowFloorService showFloorService) {
        this.showFloorService = showFloorService;
    }

    @PostMapping
    public void postShowFloor(@Valid @RequestBody ShowFloorSaveRequest request) {

        showFloorService.createShowFloor(request);
    }
}
