package com.culture.ticketing.show.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.show.application.dto.ShowScheduleSaveRequest;
import com.culture.ticketing.show.domain.ShowSchedule;
import com.culture.ticketing.show.exception.DuplicatedShowScheduleException;
import com.culture.ticketing.show.infra.ShowScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (request.getShowId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_ID);
        }
        if (request.getShowScheduleDate() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SCHEDULE_DATE);
        }
        if (request.getShowScheduleTime() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SCHEDULE_TIME);
        }

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
