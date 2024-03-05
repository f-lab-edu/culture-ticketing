package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse;
import com.culture.ticketing.show.application.dto.ShowAreaResponse;
import com.culture.ticketing.show.application.dto.ShowAreasResponse;
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;
import com.culture.ticketing.show.exception.DuplicatedShowSeatException;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowAreaService showAreaService;
    private final ShowAreaGradeService showAreaGradeService;

    public ShowSeatService(ShowSeatRepository showSeatRepository, ShowAreaService showAreaService, ShowAreaGradeService showAreaGradeService) {
        this.showSeatRepository = showSeatRepository;
        this.showAreaService = showAreaService;
        this.showAreaGradeService = showAreaGradeService;
    }

    @Transactional
    public void createShowSeat(ShowSeatSaveRequest request) {

        checkValidShowSeatSaveRequest(request);

        if (showAreaService.notExistsById(request.getShowAreaId())) {
            throw new ShowAreaNotFoundException(request.getShowAreaId());
        }

        ShowSeat showSeat = request.toEntity();
        checkDuplicatedSeat(showSeat);
        showSeatRepository.save(showSeat);
    }

    private void checkValidShowSeatSaveRequest(ShowSeatSaveRequest request) {

        Objects.requireNonNull(request.getShowAreaId(), "공연 구역 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getShowSeatNumber() > 0, "좌석 번호를 1 이상 숫자로 입력해주세요.");
    }

    private void checkDuplicatedSeat(ShowSeat showSeat) {
        showSeatRepository.findByShowAreaIdAndShowSeatRowAndShowSeatNumber(showSeat.getShowAreaId(), showSeat.getShowSeatRow(), showSeat.getShowSeatNumber())
                .ifPresent(s -> {
                    throw new DuplicatedShowSeatException();
                });
    }

    @Transactional(readOnly = true)
    public List<ShowSeat> findShowSeatsByShowAreaId(Long showAreaId) {

        return showSeatRepository.findByShowAreaId(showAreaId);
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowSeatIds(Set<Long> showSeatIds) {

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        List<Long> showAreaIds = showSeats.stream()
                .map(ShowSeat::getShowAreaId)
                .collect(Collectors.toList());

        ShowAreasResponse showAreas = showAreaService.findShowAreasByShowAreaIds(showAreaIds);

        return showSeatRepository.findAllById(showSeatIds).stream()
                .map(ShowSeat::getShowAreaId)
                .map(showAreas::getByShowAreaId)
                .mapToInt(ShowAreaResponse::getPrice)
                .sum();
    }

    @Transactional(readOnly = true)
    public List<ShowSeat> findByIds(List<Long> showSeatIds) {

        return showSeatRepository.findAllById(showSeatIds);
    }

    @Transactional(readOnly = true)
    public ShowSeatCountsResponse findShowSeatCountsByShowId(Long showId) {

        ShowAreaGradesResponse showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);
        ShowAreasResponse showAreas = showAreaService.findShowAreasByShowId(showId);
        List<ShowSeat> showSeats =  showSeatRepository.findByShowAreaIdIn(showAreas.getShowAreaIds());

        return new ShowSeatCountsResponse(showSeats, showAreas, showAreaGrades);
    }
}
