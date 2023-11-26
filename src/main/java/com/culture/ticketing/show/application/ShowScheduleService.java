package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowScheduleSaveRequest;
import com.culture.ticketing.show.domain.ShowSchedule;
import com.culture.ticketing.show.exception.DuplicatedShowScheduleException;
import com.culture.ticketing.show.infra.ShowScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.*;

@Service
public class ShowScheduleService {

    private final ShowScheduleRepository showScheduleRepository;
    private final ShowService showService;

    public ShowScheduleService(ShowScheduleRepository showScheduleRepository, ShowService showService) {
        this.showScheduleRepository = showScheduleRepository;
        this.showService = showService;
    }

    @Transactional
    public void createShowSchedule(ShowScheduleSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(request.getShowScheduleDate(), EMPTY_SHOW_SCHEDULE_DATE.getMessage());
        Objects.requireNonNull(request.getShowScheduleTime(), EMPTY_SHOW_SCHEDULE_TIME.getMessage());

        showService.getShowByShowId(request.getShowId());
        ShowSchedule showSchedule = request.toEntity();
        checkDuplicatedShowSchedule(showSchedule);
        showScheduleRepository.save(showSchedule);
    }

    public void checkDuplicatedShowSchedule(ShowSchedule showSchedule) {
        showScheduleRepository.findByShowIdAndShowScheduleDateAndShowScheduleTime(
                showSchedule.getShowId(),
                showSchedule.getShowScheduleDate(),
                showSchedule.getShowScheduleTime()).ifPresent(schedule -> {
            throw new DuplicatedShowScheduleException();
        });
    }
}
