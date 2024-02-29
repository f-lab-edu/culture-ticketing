package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @PostMapping
    public void postShowSeat(@RequestBody ShowSeatSaveRequest request) {

        showSeatService.createShowSeat(request);
    }

    @GetMapping
    public List<ShowSeatResponse> getShowSeatsByShowAreaId(@RequestParam("showAreaId") Long showAreaId) {

        return showSeatService.findShowSeatsByShowAreaId(showAreaId);
    }
}
