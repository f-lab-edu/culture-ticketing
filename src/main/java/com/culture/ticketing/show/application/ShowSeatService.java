package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.application.dto.ShowAreaResponse;
import com.culture.ticketing.show.application.dto.ShowSeatCountResponse;
import com.culture.ticketing.show.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;
import com.culture.ticketing.show.exception.DuplicatedShowSeatException;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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
    public List<ShowSeatResponse> findShowSeatsByShowAreaId(Long showAreaId) {

        return getShowSeatResponses(showSeatRepository.findByShowAreaId(showAreaId));
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowSeatIds(Set<Long> showSeatIds) {

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        List<Long> showAreaIds = getShowAreaIds(showSeats, ShowSeat::getShowAreaId);

        List<ShowAreaResponse> showAreas = showAreaService.findShowAreasByShowAreaIds(showAreaIds);

        Map<Long, ShowAreaResponse> showAreaMapById = getShowAreaResponseMapById(showAreas);

        return showSeats.stream()
                .map(ShowSeat::getShowAreaId)
                .map(showAreaMapById::get)
                .mapToInt(ShowAreaResponse::getPrice)
                .sum();
    }

    @Transactional(readOnly = true)
    public List<ShowSeatResponse> findByIds(List<Long> showSeatIds) {

        return getShowSeatResponses(showSeatRepository.findAllById(showSeatIds));
    }

    private List<ShowSeatResponse> getShowSeatResponses(List<ShowSeat> showSeats) {

        return showSeats.stream()
                .map(ShowSeatResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShowSeatCountResponse> findShowSeatCountsByShowId(Long showId) {

        List<ShowAreaGradeResponse> showAreaGrades = showAreaGradeService.findShowAreaGradesByShowId(showId);

        Map<Long, Long> showSeatCountMapByShowAreaGradeId = getShowSeatCountMapByShowAreaGradeId(showId);

        return showAreaGrades.stream()
                .map(showAreaGrade -> new ShowSeatCountResponse(showAreaGrade, showSeatCountMapByShowAreaGradeId.getOrDefault(showAreaGrade.getShowAreaGradeId(), 0L)))
                .collect(Collectors.toList());
    }

    private Map<Long, Long> getShowSeatCountMapByShowAreaGradeId(Long showId) {

        List<ShowAreaResponse> showAreas = showAreaService.findShowAreasByShowId(showId);

        Map<Long, ShowAreaResponse> showAreaMapById = getShowAreaResponseMapById(showAreas);

        List<Long> showAreaIds = getShowAreaIds(showAreas, ShowAreaResponse::getShowAreaId);

        List<ShowSeat> showSeats = showSeatRepository.findByShowAreaIdIn(showAreaIds);

        return showSeats.stream()
                .collect(Collectors.groupingBy(showSeat -> showAreaMapById.get(showSeat.getShowAreaId()).getShowAreaGradeId(), Collectors.counting()));
    }

    private <T, R> List<R> getShowAreaIds(List<T> list, Function<T, R> function) {

        return list.stream()
                .map(function)
                .collect(Collectors.toList());
    }

    private Map<Long, ShowAreaResponse> getShowAreaResponseMapById(List<ShowAreaResponse> showAreas) {

        return showAreas.stream()
                .collect(Collectors.toMap(ShowAreaResponse::getShowAreaId, Function.identity()));
    }

}
