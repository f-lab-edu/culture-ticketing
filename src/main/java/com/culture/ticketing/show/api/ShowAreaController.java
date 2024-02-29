package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowAreaService;
import com.culture.ticketing.show.application.dto.ShowAreaResponse;
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/show-areas")
public class ShowAreaController {

    private final ShowAreaService showAreaService;

    public ShowAreaController(ShowAreaService showAreaService) {
        this.showAreaService = showAreaService;
    }

    @PostMapping
    public void postShowArea(@RequestBody ShowAreaSaveRequest request) {

        showAreaService.createShowArea(request);
    }

    @GetMapping
    public List<ShowAreaResponse> getShowAreasByShowId(@RequestParam("showId") Long showId) {

        return showAreaService.findShowAreasByShowId(showId);
    }
}
