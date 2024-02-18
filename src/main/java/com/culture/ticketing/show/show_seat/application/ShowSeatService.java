package com.culture.ticketing.show.show_seat.application;

import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import com.culture.ticketing.show.show_seat.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.show_seat.infra.ShowSeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

        checkValidShowSeatSaveRequest(request);

        if (showSeatGradeService.notExistsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        seatService.checkSeatsExists(request.getSeatIds());

        List<ShowSeat> showSeats = request.toEntities();
        showSeatRepository.saveAll(showSeats);
    }

    private void checkValidShowSeatSaveRequest(ShowSeatSaveRequest request) {

        Objects.requireNonNull(request.getShowSeatGradeId(), "공연 좌석 등급 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getSeatIds(), "좌석 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getSeatIds().size() != 0, "좌석 아이디를 입력해주세요.");
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowSeatIds(Set<Long> showSeatIds) {

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        List<Long> showSeatGradeIds = showSeats.stream()
                .map(ShowSeat::getShowSeatGradeId)
                .collect(Collectors.toList());

        Map<Long, Integer> priceMapByShowSeatGradeId = showSeatGradeService.findByIds(showSeatGradeIds).stream()
                .collect(Collectors.toMap(ShowSeatGrade::getShowSeatGradeId, ShowSeatGrade::getPrice));

        return showSeats.stream()
                .map(ShowSeat::getShowSeatGradeId)
                .mapToInt(priceMapByShowSeatGradeId::get)
                .sum();
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> countMapByShowSeatGradeId(List<Long> showSeatGradeIds) {

        return showSeatRepository.findByShowSeatGradeIdIn(showSeatGradeIds).stream()
                .collect(Collectors.groupingBy(ShowSeat::getShowSeatGradeId, Collectors.counting()));
    }

    public List<ShowSeat> findByIds(List<Long> showSeatIds) {

        return showSeatRepository.findAllById(showSeatIds);
    }
}
