package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowFloorResponse;
import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.ShowFloor;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowFloorRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShowFloorService {

    private final ShowFloorRepository showFloorRepository;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowFloorService(ShowFloorRepository showFloorRepository, ShowSeatGradeService showSeatGradeService) {
        this.showFloorRepository = showFloorRepository;
        this.showSeatGradeService = showSeatGradeService;
    }

    @Transactional
    public void createShowFloor(ShowFloorSaveRequest request) {

        Objects.requireNonNull(request.getShowSeatGradeId(), "공연 좌석 등급 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowFloorName()), "공연 플로어 구역명을 입력해주세요.");
        Preconditions.checkArgument(request.getCount() > 0, "공연 플로어 인원수를 1 이상 숫자로 입력해주세요.");

        if (showSeatGradeService.notExistsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        showFloorRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<ShowFloor> findByIds(List<Long> showFloorIds) {

        return showFloorRepository.findAllById(showFloorIds);
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> countMapByShowSeatGradeId(List<Long> showSeatGradeIds) {

        return showFloorRepository.findByShowSeatGradeIdIn(showSeatGradeIds).stream()
                .collect(Collectors.groupingBy(ShowFloor::getShowSeatGradeId, Collectors.summingLong(ShowFloor::getCount)));
    }

    public List<ShowFloorResponse> findByShowId(Long showId) {

        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);
        List<Long> showSeatGradeIds = showSeatGrades.stream()
                .map(ShowSeatGradeResponse::getShowSeatGradeId)
                .collect(Collectors.toList());

        return showFloorRepository.findByShowSeatGradeIdIn(showSeatGradeIds).stream()
                .map(ShowFloorResponse::from)
                .collect(Collectors.toList());
    }
}
