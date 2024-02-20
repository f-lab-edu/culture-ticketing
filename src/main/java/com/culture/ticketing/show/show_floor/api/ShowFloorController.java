package com.culture.ticketing.show.show_floor.api;

import com.culture.ticketing.show.show_floor.application.ShowFloorService;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/show-floors")
public class ShowFloorController {

    private final ShowFloorService showFloorService;

    public ShowFloorController(ShowFloorService showFloorService) {
        this.showFloorService = showFloorService;
    }

    @PostMapping
    public void postShowFloor(@RequestBody ShowFloorSaveRequest request) {

        showFloorService.createShowFloor(request);
    }
}