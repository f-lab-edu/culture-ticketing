package com.culture.ticketing.show.show_seat.api;

import com.culture.ticketing.show.show_seat.application.ShowSeatFacadeService;
import com.culture.ticketing.show.show_seat.application.ShowSeatService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatSaveRequest;
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
    private final ShowSeatFacadeService showSeatFacadeService;

    public ShowSeatController(ShowSeatService showSeatService, ShowSeatFacadeService showSeatFacadeService) {
        this.showSeatService = showSeatService;
        this.showSeatFacadeService = showSeatFacadeService;
    }

    @PostMapping
    public void postShowSeat(@RequestBody ShowSeatSaveRequest request) {

        showSeatService.createShowSeat(request);
    }

    @GetMapping
    public List<ShowSeatResponse> getShowSeats(@RequestParam("roundId") Long roundId, @RequestParam("areaId") Long areaId) {

        return showSeatFacadeService.findByRoundIdAndAreaId(roundId, areaId);
    }
}
