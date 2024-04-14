package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradeSaveRequest;
import com.culture.ticketing.show.domain.ShowAreaGrade;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.ShowAreaGradeRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShowAreaGradeService {

    private final ShowAreaGradeRepository showAreaGradeRepository;
    private final ShowService showService;

    public ShowAreaGradeService(ShowAreaGradeRepository showAreaGradeRepository, ShowService showService) {
        this.showAreaGradeRepository = showAreaGradeRepository;
        this.showService = showService;
    }

    @Transactional
    public void createShowAreaGrade(ShowAreaGradeSaveRequest request) {

        checkValidShowAreaGradeSaveRequest(request);

        if (showService.notExistsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        showAreaGradeRepository.save(request.toEntity());
    }

    private void checkValidShowAreaGradeSaveRequest(ShowAreaGradeSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowAreaGradeName()), "공연 구역 등급명을 입력해주세요.");
        Preconditions.checkArgument(request.getPrice() >= 0, "공연 구역 등급의 가격은 0 이상으로 입력해주세요.");
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long showAreaGradeId) {
        return !showAreaGradeRepository.existsById(showAreaGradeId);
    }

    @Transactional(readOnly = true)
    public List<ShowAreaGradeResponse> findShowAreaGradesByShowId(Long showId) {

        return getShowAreaGradeResponses(showAreaGradeRepository.findByShowId(showId));
    }

    @Transactional(readOnly = true)
    public List<ShowAreaGradeResponse> findShowAreaGradesByIds(List<Long> showAreaGradeIds) {

        return getShowAreaGradeResponses(showAreaGradeRepository.findAllById(showAreaGradeIds));
    }

    private List<ShowAreaGradeResponse> getShowAreaGradeResponses(List<ShowAreaGrade> showAreaGrades) {

        return showAreaGrades.stream()
                .map(ShowAreaGradeResponse::new)
                .collect(Collectors.toList());
    }
}
