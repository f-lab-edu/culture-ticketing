package com.culture.ticketing.show.show_seat.application;

import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeSaveRequest;
import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.show_seat.infra.ShowSeatGradeRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        checkValidShowSeatGradeSaveRequest(request);

        if (showService.notExistsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        showSeatGradeRepository.save(request.toEntity());
    }

    private void checkValidShowSeatGradeSaveRequest(ShowSeatGradeSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowSeatGradeName()), "공연 좌석 등급을 입력해주세요.");
        Preconditions.checkArgument(request.getPrice() >= 0, "공연 좌석 가격을 0 이상으로 입력해주세요.");
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long showSeatGradeId) {
        return !showSeatGradeRepository.existsById(showSeatGradeId);
    }

    @Transactional(readOnly = true)
    public List<ShowSeatGradeResponse> findShowSeatGradesByShowId(Long showId) {

        return showSeatGradeRepository.findByShowId(showId).stream()
                .map(ShowSeatGradeResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShowSeatGrade> findByIds(List<Long> showSeatGradeIds) {

        return showSeatGradeRepository.findAllById(showSeatGradeIds);
    }
}