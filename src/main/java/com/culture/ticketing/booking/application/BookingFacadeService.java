package com.culture.ticketing.booking.application;

import com.culture.ticketing.show.application.ShowFloorService;
import com.culture.ticketing.show.application.ShowSeatGradeService;
import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.ShowFloor;
import com.culture.ticketing.show.domain.ShowSeat;
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

    public BookingFacadeService(BookingShowFloorService bookingShowFloorService, BookingShowSeatService bookingShowSeatService,
                                ShowSeatService showSeatService, ShowFloorService showFloorService, ShowSeatGradeService showSeatGradeService) {
        this.bookingShowFloorService = bookingShowFloorService;
        this.bookingShowSeatService = bookingShowSeatService;
        this.showSeatService = showSeatService;
        this.showFloorService = showFloorService;
        this.showSeatGradeService = showSeatGradeService;
    }
    @Transactional(readOnly = true)
    public Map<Long, Map<ShowSeatGradeResponse, Long>> findShowSeatAvailableCountMapByShowSeatGradeAndRoundId(Long showId, List<Long> roundIds) {

        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);
        List<Long> showSeatGradeIds = showSeatGrades.stream()
                .map(ShowSeatGradeResponse::getShowSeatGradeId)
                .collect(Collectors.toList());

        Map<Long, Long> showSeatCntMapByShowSeatGradeId = showSeatService.countMapByShowSeatGradeId(showSeatGradeIds);
        Map<Long, Long> showFloorCntMapByShowSeatGradeId = showFloorService.countMapByShowSeatGradeId(showSeatGradeIds);

        Map<Long, List<ShowSeat>> bookingShowSeatsMapByRoundId = bookingShowSeatService.findBookingShowSeatsMapByRoundId(roundIds);
        Map<Long, List<ShowFloor>> bookingShowFloorsMapByRoundId = bookingShowFloorService.findBookingShowFloorsMapByRoundId(roundIds);

        return roundIds.stream()
                .collect(Collectors.toMap(Function.identity(), roundId -> {

                    Map<Long, Long> bookingShowSeatCntMapByShowSeatGradeId = bookingShowSeatsMapByRoundId.getOrDefault(roundId, new ArrayList<>()).stream()
                            .collect(Collectors.groupingBy(ShowSeat::getShowSeatGradeId, Collectors.counting()));
                    Map<Long, Long> bookingShowFloorCntMapByShowSeatGradeId = bookingShowFloorsMapByRoundId.getOrDefault(roundId, new ArrayList<>()).stream()
                            .collect(Collectors.groupingBy(ShowFloor::getShowSeatGradeId, Collectors.counting()));

                    return showSeatGrades.stream()
                            .collect(Collectors.toMap(Function.identity(), showSeatGrade -> showSeatCntMapByShowSeatGradeId.getOrDefault(showSeatGrade.getShowSeatGradeId(), 0L)
                                    + showFloorCntMapByShowSeatGradeId.getOrDefault(showSeatGrade.getShowSeatGradeId(), 0L)
                                    - bookingShowSeatCntMapByShowSeatGradeId.getOrDefault(showSeatGrade.getShowSeatGradeId(), 0L)
                                    - bookingShowFloorCntMapByShowSeatGradeId.getOrDefault(showSeatGrade.getShowSeatGradeId(), 0L)));

                }));
    }
}
