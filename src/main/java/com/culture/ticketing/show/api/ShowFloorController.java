package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFloorService;
import com.culture.ticketing.show.application.dto.ShowFloorResponse;
import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/show-floors")
public class ShowFloorController {

    private final ShowFloorService showFloorService;

    public ShowFloorController(ShowFloorService showFloorService) {
        this.showFloorService = showFloorService;
    }

    @PostMapping
    public void postShowFloor(@Valid @RequestBody ShowFloorSaveRequest request) {

        showFloorService.createShowFloor(request);
    }

    @GetMapping
    public List<ShowFloorResponse> getShowFloorsByShowId(@RequestParam("showId") Long showId) {

        return showFloorService.findByShowId(showId);
    }
}
