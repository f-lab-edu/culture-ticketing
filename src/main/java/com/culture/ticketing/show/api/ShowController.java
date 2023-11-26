package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowScheduleService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowScheduleSaveRequest;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowScheduleService showScheduleService;

    public ShowController(ShowService showService, ShowScheduleService showScheduleService) {
        this.showService = showService;
        this.showScheduleService = showScheduleService;
    }

    @PostMapping("")
    public void postShow(@Valid @RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @PostMapping("/schedules")
    public void postShowSchedule(@Valid @RequestBody ShowScheduleSaveRequest request) {

        showScheduleService.createShowSchedule(request);
    }

    @GetMapping("")
    public List<ShowResponse> getShows(@RequestParam(name = "offset") Long offset,
                                       @RequestParam(name = "size") int size) {

        return showService.getShows(offset, size);
    }
}
