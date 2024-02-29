package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.exception.ShowAreaNotFoundException;
import com.culture.ticketing.place.exception.DuplicatedShowSeatException;
import com.culture.ticketing.place.exception.SeatNotFoundException;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowAreaService showAreaService;

    public ShowSeatService(ShowSeatRepository showSeatRepository, ShowAreaService showAreaService) {
        this.showSeatRepository = showSeatRepository;
        this.showAreaService = showAreaService;
    }

    @Transactional
    public void createShowSeat(ShowSeatSaveRequest request) {

        checkValidShowSeatSaveRequest(request);

        if (showAreaService.notExistsById(request.getShowAreaId())) {
            throw new ShowAreaNotFoundException(request.getShowAreaId()));
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
    public List<ShowSeat> findBySeatIds(List<Long> seatIds) {

        return showSeatRepository.findAllById(seatIds);
    }

    public List<ShowSeatResponse> findShowSeatsByShowAreaId(Long showAreaId) {

        return showSeatRepository.findByShowAreaId(showAreaId).stream()
                .map(ShowSeatResponse::new)
                .collect(Collectors.toList());
    }
}
