package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFloorGradeService;
import com.culture.ticketing.show.application.dto.ShowFloorGradeSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/show-floor-grades")
public class ShowFloorGradeController {

    private final ShowFloorGradeService showFloorGradeService;

    public ShowFloorGradeController(ShowFloorGradeService showFloorGradeService) {
        this.showFloorGradeService = showFloorGradeService;
    }

    @PostMapping
    public void postShowFloorGrade(@RequestBody ShowFloorGradeSaveRequest request) {

        showFloorGradeService.createShowFloorGrade(request);
    }
}
