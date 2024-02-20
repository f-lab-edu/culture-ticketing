package com.culture.ticketing.show.show_floor.api;


import com.culture.ticketing.show.show_floor.application.dto.ShowFloorResponse;
import com.culture.ticketing.show.show_floor.application.ShowFloorService;
import org.springframework.web.bind.annotation.GetMapping;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @GetMapping
    public List<ShowFloorResponse> getShowFloorsByShowId(@RequestParam("showId") Long showId) {

        return showFloorService.findByShowId(showId);
    }
}
