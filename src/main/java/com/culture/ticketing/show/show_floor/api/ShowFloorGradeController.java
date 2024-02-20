package com.culture.ticketing.show.show_floor.api;

import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeSaveRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    public List<ShowFloorGradeResponse> getShowFloorGradesByShowId(@RequestParam("showId") Long showId) {

        return showFloorGradeService.findShowFloorGradesByShowId(showId);
    }
}
