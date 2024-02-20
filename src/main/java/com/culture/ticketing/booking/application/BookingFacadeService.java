package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowFloorsMapByRoundIdResponse;
import com.culture.ticketing.booking.application.dto.BookingShowSeatsMapByRoundIdResponse;
import com.culture.ticketing.booking.application.dto.ShowFloorGradeWithCountMapByRoundIdResponse;
import com.culture.ticketing.booking.application.dto.ShowSeatGradeWithCountMapByRoundIdResponse;
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService;
import com.culture.ticketing.show.show_floor.application.ShowFloorService;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorCountMapByShowFloorGradeId;
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService;
import com.culture.ticketing.show.show_seat.application.ShowSeatService;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatCountMapByShowSeatGradeIdResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public ShowSeatGradeWithCountMapByRoundIdResponse findShowSeatGradeWithAvailableCountMapByRoundId(Long showId, List<Long> roundIds) {

        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);
        List<Long> showSeatGradeIds = showSeatGrades.stream()
                .map(ShowSeatGradeResponse::getShowSeatGradeId)
                .collect(Collectors.toList());

        ShowSeatCountMapByShowSeatGradeIdResponse showSeatCountMapByShowSeatGradeId = showSeatService.countMapByShowSeatGradeId(showSeatGradeIds);

        BookingShowSeatsMapByRoundIdResponse bookingShowSeatsMapByRoundId = bookingShowSeatService.findBookingShowSeatsMapByRoundId(roundIds);

        return new ShowSeatGradeWithCountMapByRoundIdResponse(roundIds, showSeatGrades, showSeatCountMapByShowSeatGradeId, bookingShowSeatsMapByRoundId);
    }

    public ShowFloorGradeWithCountMapByRoundIdResponse findShowFloorGradeWithAvailableCountMapByRoundId(Long showId, List<Long> roundIds) {

        List<ShowFloorGradeResponse> showFloorGrades = showFloorGradeService.findShowFloorGradesByShowId(showId);
        List<Long> showFloorGradeIds = showFloorGrades.stream()
                .map(ShowFloorGradeResponse::getShowFloorGradeId)
                .collect(Collectors.toList());

        ShowFloorCountMapByShowFloorGradeId showFloorCountMapByShowFloorGradeId = showFloorService.countMapByShowFloorGradeId(showFloorGradeIds);

        BookingShowFloorsMapByRoundIdResponse bookingShowFloorsMapByRoundId = bookingShowFloorService.findBookingShowFloorsMapByRoundId(roundIds);

        return new ShowFloorGradeWithCountMapByRoundIdResponse(roundIds, showFloorGrades, showFloorCountMapByShowFloorGradeId, bookingShowFloorsMapByRoundId);
    }
}
