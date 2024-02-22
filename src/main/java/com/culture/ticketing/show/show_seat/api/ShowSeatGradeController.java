package com.culture.ticketing.show.show_seat.api;

import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/show-seat-grades")
public class ShowSeatGradeController {

    private final ShowSeatGradeService showSeatGradeService;

    public ShowSeatGradeController(ShowSeatGradeService showSeatGradeService) {
        this.showSeatGradeService = showSeatGradeService;
    }

    @PostMapping
    public void postShowSeatGrade(@RequestBody ShowSeatGradeSaveRequest request) {

        showSeatGradeService.createShowSeatGrade(request);
    }
}
