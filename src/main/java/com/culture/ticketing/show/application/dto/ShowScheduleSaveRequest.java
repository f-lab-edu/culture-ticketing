package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ShowScheduleSaveRequest {

    @NotNull
    private Long showId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime showScheduleDateTime;

    public ShowSchedule toEntity() {
        return ShowSchedule.builder()
                .showId(showId)
                .showScheduleDateTime(showScheduleDateTime)
                .build();
    }
}
