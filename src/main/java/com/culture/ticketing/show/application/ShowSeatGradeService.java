package com.culture.ticketing.show.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest;
import com.culture.ticketing.show.domain.ShowSeatGrade;
import com.culture.ticketing.show.infra.ShowSeatGradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ShowSeatGradeService {

    private final ShowSeatGradeRepository showSeatGradeRepository;
    private final ShowService showService;

    public ShowSeatGradeService(ShowSeatGradeRepository showSeatGradeRepository, ShowService showService) {
        this.showSeatGradeRepository = showSeatGradeRepository;
        this.showService = showService;
    }

    @Transactional
    public void createShowSeatGrade(ShowSeatGradeSaveRequest request) {

        if (!StringUtils.hasText(request.getSeatGrade())) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE);
        }
        if (request.getPrice() < 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SHOW_SEAT_PRICE);
        }
        if (request.getShowId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_ID);
        }

        showService.getShowByShowId(request.getShowId());
        ShowSeatGrade showSeatGrade = request.toEntity();
        showSeatGradeRepository.save(showSeatGrade);
    }
}
