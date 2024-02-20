package com.culture.ticketing.show.show_floor.application;

import com.culture.ticketing.show.show_floor.application.dto.ShowFloorResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorCountMapByShowFloorGradeId;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorSaveRequest;
import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade;
import com.culture.ticketing.show.show_floor.exception.ShowFloorGradeNotFoundException;
import com.culture.ticketing.show.show_floor.infra.ShowFloorRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowFloorService {

    private final ShowFloorRepository showFloorRepository;
    private final ShowFloorGradeService showFloorGradeService;

    public ShowFloorService(ShowFloorRepository showFloorRepository, ShowFloorGradeService showFloorGradeService) {
        this.showFloorRepository = showFloorRepository;
        this.showFloorGradeService = showFloorGradeService;
    }

    @Transactional
    public void createShowFloor(ShowFloorSaveRequest request) {

        checkValidShowFloorSaveRequest(request);

        if (showFloorGradeService.notExistsById(request.getShowFloorGradeId())) {
            throw new ShowFloorGradeNotFoundException(request.getShowFloorGradeId());
        }

        showFloorRepository.save(request.toEntity());
    }

    private void checkValidShowFloorSaveRequest(ShowFloorSaveRequest request) {

        Objects.requireNonNull(request.getShowFloorGradeId(), "공연 플로어 등급 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowFloorName()), "공연 플로어 구역명을 입력해주세요.");
        Preconditions.checkArgument(request.getCount() > 0, "공연 플로어 인원수를 1 이상 숫자로 입력해주세요.");
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowFloorIds(List<Long> showFloorIds) {

        List<ShowFloor> showFloors = showFloorRepository.findAllById(showFloorIds);
        Map<Long, ShowFloor> showFloorMapById = showFloors.stream()
                .collect(Collectors.toMap(ShowFloor::getShowFloorId, Function.identity()));

        List<Long> showFloorGradeIds = showFloors.stream()
                .map(ShowFloor::getShowFloorGradeId)
                .collect(Collectors.toList());

        Map<Long, Integer> priceMapByShowFloorGradeId = showFloorGradeService.findByIds(showFloorGradeIds).stream()
                .collect(Collectors.toMap(ShowFloorGrade::getShowFloorGradeId, ShowFloorGrade::getPrice));

        return showFloorIds.stream()
                .map(showFloorMapById::get)
                .map(ShowFloor::getShowFloorGradeId)
                .mapToInt(priceMapByShowFloorGradeId::get)
                .sum();
    }

    @Transactional(readOnly = true)
    public ShowFloorCountMapByShowFloorGradeId countMapByShowFloorGradeId(List<Long> showFloorGradeIds) {

        return new ShowFloorCountMapByShowFloorGradeId(showFloorRepository.findByShowFloorGradeIdIn(showFloorGradeIds));
    }

    @Transactional(readOnly = true)
    public List<ShowFloor> findByIds(List<Long> showFloorIds) {

        return showFloorRepository.findAllById(showFloorIds);
    }

    public List<ShowFloorResponse> findByShowId(Long showId) {

        List<ShowFloorGradeResponse> showFloorGrades = showFloorGradeService.findShowFloorGradesByShowId(showId);
        List<Long> showFloorGradeIds = showFloorGrades.stream()
                .map(ShowFloorGradeResponse::getShowFloorGradeId)
                .collect(Collectors.toList());

        return showFloorRepository.findByShowFloorGradeIdIn(showFloorGradeIds).stream()
                .map(ShowFloorResponse::from)
                .collect(Collectors.toList());
    }
}
