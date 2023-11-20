package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ShowScheduleSaveRequest {

    @NotNull
    private Long showId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate showScheduleDate;
    @NotNull
    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime showScheduleTime;

    public ShowSchedule toEntity() {
        return ShowSchedule.builder()
                .showId(showId)
                .showScheduleDate(showScheduleDate)
                .showScheduleTime(showScheduleTime)
                .build();
    }
}
