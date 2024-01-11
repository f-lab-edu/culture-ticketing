package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatGradeService showSeatGradeService;
    private final SeatService seatService;

    public ShowSeatService(ShowSeatRepository showSeatRepository, ShowSeatGradeService showSeatGradeService, SeatService seatService) {
        this.showSeatRepository = showSeatRepository;
        this.showSeatGradeService = showSeatGradeService;
        this.seatService = seatService;
    }

    @Transactional
    public void createShowSeat(ShowSeatSaveRequest request) {

        Objects.requireNonNull(request.getShowSeatGradeId(), "공연 좌석 등급 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getSeatIds(), "좌석 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getSeatIds().size() != 0, "좌석 아이디를 입력해주세요.");

        if (showSeatGradeService.notExistsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        seatService.checkSeatsExists(request.getSeatIds());

        List<ShowSeat> showSeats = request.toEntities();
        showSeatRepository.saveAll(showSeats);
    }

    @Transactional(readOnly = true)
    public List<ShowSeat> findByIds(List<Long> showSeatIds) {

        return showSeatRepository.findAllById(showSeatIds);
    }
}
