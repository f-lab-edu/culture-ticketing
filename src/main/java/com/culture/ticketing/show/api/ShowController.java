package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowScheduleService;
import com.culture.ticketing.show.application.ShowSeatGradeService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowScheduleSaveRequest;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowScheduleService showScheduleService;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowController(ShowService showService, ShowScheduleService showScheduleService, ShowSeatGradeService showSeatGradeService) {
        this.showService = showService;
        this.showScheduleService = showScheduleService;
        this.showSeatGradeService = showSeatGradeService;
    }

    @PostMapping("")
    public void postShow(@Valid @RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @PostMapping("/schedules")
    public void postShowSchedule(@Valid @RequestBody ShowScheduleSaveRequest request) {

        showScheduleService.createShowSchedule(request);
    }

    @PostMapping("/seat-grades")
    public void postShowSeatGrade(@Valid @RequestBody ShowSeatGradeSaveRequest request) {

        showSeatGradeService.createShowSeatGrade(request);
    }
}
