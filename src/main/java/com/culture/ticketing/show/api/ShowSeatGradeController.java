package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowSeatGradeService;
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/seat-grades")
public class ShowSeatGradeController {

    private final ShowSeatGradeService showSeatGradeService;

    public ShowSeatGradeController(ShowSeatGradeService showSeatGradeService) {
        this.showSeatGradeService = showSeatGradeService;
    }

    @PostMapping("")
    public void postShowSeatGrade(@Valid @RequestBody ShowSeatGradeSaveRequest request) {

        showSeatGradeService.createShowSeatGrade(request);
    }
}
