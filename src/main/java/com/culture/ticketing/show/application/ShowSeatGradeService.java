package com.culture.ticketing.show.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest;
import com.culture.ticketing.show.domain.ShowSeatGrade;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowSeatGradeRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SHOW_SEAT_PRICE;

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

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getSeatGrade()), EMPTY_SHOW_SEAT_GRADE.getMessage());
        Preconditions.checkArgument(request.getPrice() > 0, NEGATIVE_SHOW_SEAT_PRICE.getMessage());

        if (showService.existsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        ShowSeatGrade showSeatGrade = request.toEntity();
        showSeatGradeRepository.save(showSeatGrade);
    }

    @Transactional(readOnly = true)
    public ShowSeatGrade getShowSeatGradeByShowSeatGradeId(Long showSeatGradeId) {
        return showSeatGradeRepository.findById(showSeatGradeId).orElseThrow(ShowSeatGradeNotFoundException::new);
    }
}
