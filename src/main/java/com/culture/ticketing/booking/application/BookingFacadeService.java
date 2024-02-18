package com.culture.ticketing.booking.application;

import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService;
import com.culture.ticketing.show.show_floor.application.ShowFloorService;
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService;
import com.culture.ticketing.show.show_seat.application.ShowSeatService;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingFacadeService {

    private final BookingShowFloorService bookingShowFloorService;
    private final BookingShowSeatService bookingShowSeatService;
    private final ShowSeatService showSeatService;
    private final ShowFloorService showFloorService;
    private final ShowSeatGradeService showSeatGradeService;
    private final ShowFloorGradeService showFloorGradeService;

    public BookingFacadeService(BookingShowFloorService bookingShowFloorService, BookingShowSeatService bookingShowSeatService,
                                ShowSeatService showSeatService, ShowFloorService showFloorService,
                                ShowSeatGradeService showSeatGradeService, ShowFloorGradeService showFloorGradeService) {
        this.bookingShowFloorService = bookingShowFloorService;
        this.bookingShowSeatService = bookingShowSeatService;
        this.showSeatService = showSeatService;
        this.showFloorService = showFloorService;
        this.showSeatGradeService = showSeatGradeService;
        this.showFloorGradeService = showFloorGradeService;
    }
    @Transactional(readOnly = true)
    public Map<Long, Map<ShowSeatGradeResponse, Long>> findShowSeatAvailableCountMapByShowSeatGradeAndRoundId(Long showId, List<Long> roundIds) {

        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);
        List<Long> showSeatGradeIds = showSeatGrades.stream()
                .map(ShowSeatGradeResponse::getShowSeatGradeId)
                .collect(Collectors.toList());

        Map<Long, Long> showSeatCntMapByShowSeatGradeId = showSeatService.countMapByShowSeatGradeId(showSeatGradeIds);

        Map<Long, List<ShowSeat>> bookingShowSeatsMapByRoundId = bookingShowSeatService.findBookingShowSeatsMapByRoundId(roundIds);

        return roundIds.stream()
                .collect(Collectors.toMap(Function.identity(), roundId -> {

                    Map<Long, Long> bookingShowSeatCntMapByShowSeatGradeId = bookingShowSeatsMapByRoundId.getOrDefault(roundId, new ArrayList<>()).stream()
                            .collect(Collectors.groupingBy(ShowSeat::getShowSeatGradeId, Collectors.counting()));

                    return showSeatGrades.stream()
                            .collect(Collectors.toMap(Function.identity(), showSeatGrade -> showSeatCntMapByShowSeatGradeId.getOrDefault(showSeatGrade.getShowSeatGradeId(), 0L)
                                    - bookingShowSeatCntMapByShowSeatGradeId.getOrDefault(showSeatGrade.getShowSeatGradeId(), 0L)));

                }));
    }

    public Map<Long, Map<ShowFloorGradeResponse, Long>> findShowFloorAvailableCountMapByShowFloorGradeAndRoundId(Long showId, List<Long> roundIds) {

        List<ShowFloorGradeResponse> showFloorGrades = showFloorGradeService.findShowFloorGradesByShowId(showId);
        List<Long> showFloorGradeIds = showFloorGrades.stream()
                .map(ShowFloorGradeResponse::getShowFloorGradeId)
                .collect(Collectors.toList());

        Map<Long, Long> showFloorCntMapByShowSeatGradeId = showFloorService.countMapByShowFloorGradeId(showFloorGradeIds);

        Map<Long, List<ShowFloor>> bookingShowFloorsMapByRoundId = bookingShowFloorService.findBookingShowFloorsMapByRoundId(roundIds);

        return roundIds.stream()
                .collect(Collectors.toMap(Function.identity(), roundId -> {

                    Map<Long, Long> bookingShowFloorCntMapByShowFloorGradeId = bookingShowFloorsMapByRoundId.getOrDefault(roundId, new ArrayList<>()).stream()
                            .collect(Collectors.groupingBy(ShowFloor::getShowFloorId, Collectors.counting()));

                    return showFloorGrades.stream()
                            .collect(Collectors.toMap(Function.identity(), showFloorGrade -> showFloorCntMapByShowSeatGradeId.getOrDefault(showFloorGrade.getShowFloorGradeId(), 0L)
                                    - bookingShowFloorCntMapByShowFloorGradeId.getOrDefault(showFloorGrade.getShowFloorGradeId(), 0L)));
                }));
    }
}
