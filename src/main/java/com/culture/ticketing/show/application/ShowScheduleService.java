package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowScheduleSaveRequest;
import com.culture.ticketing.show.domain.Show;
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
        Objects.requireNonNull(request.getShowScheduleDateTime(), EMPTY_SHOW_SCHEDULE_DATE_TIME.getMessage());

        Show show = showService.findShowById(request.getShowId());
        ShowSchedule showSchedule = request.toEntity();
        checkDuplicatedShowSchedule(show, showSchedule);
        showScheduleRepository.save(showSchedule);
    }

    public void checkDuplicatedShowSchedule(Show show, ShowSchedule showSchedule) {
        showScheduleRepository.findByShowAndDuplicatedShowScheduleDateTime(
                show,
                showSchedule.getShowScheduleDateTime()).ifPresent(schedule -> {
            throw new DuplicatedShowScheduleException();
        });
    }
}
