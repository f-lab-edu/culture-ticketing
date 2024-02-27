package com.culture.ticketing.show.show_seat.application;

import com.culture.ticketing.booking.application.BookingShowSeatService;
import com.culture.ticketing.booking.application.dto.BookingShowSeatMapByShowSeatIdResponse;
import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.place.application.dto.PlaceSeatMapBySeatIdResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeMapByIdResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowSeatFacadeService {

    private final ShowSeatService showSeatService;
    private final BookingShowSeatService bookingShowSeatService;
    private final SeatService seatService;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowSeatFacadeService(ShowSeatService showSeatService, BookingShowSeatService bookingShowSeatService, SeatService seatService, ShowSeatGradeService showSeatGradeService) {
        this.showSeatService = showSeatService;
        this.bookingShowSeatService = bookingShowSeatService;
        this.seatService = seatService;
        this.showSeatGradeService = showSeatGradeService;
    }

    @Transactional(readOnly = true)
    public List<ShowSeatResponse> findByRoundIdAndAreaId(Long roundId, Long areaId) {

        List<ShowSeat> showSeats = showSeatService.findByAreaId(areaId);

        List<Long> seatIds = showSeats.stream()
                .map(ShowSeat::getSeatId)
                .collect(Collectors.toList());
        PlaceSeatMapBySeatIdResponse seatMapBySeatId = new PlaceSeatMapBySeatIdResponse(seatService.findBySeatIds(seatIds));

        List<Long> showSeatGradeIds = showSeats.stream()
                .map(ShowSeat::getShowSeatGradeId)
                .collect(Collectors.toList());
        ShowSeatGradeMapByIdResponse showSeatGradeMapById = new ShowSeatGradeMapByIdResponse(showSeatGradeService.findByIds(showSeatGradeIds));

        Set<Long> showSeatIds = showSeats.stream()
                .map(ShowSeat::getShowSeatId)
                .collect(Collectors.toSet());
        BookingShowSeatMapByShowSeatIdResponse bookingShowSeatMapByShowSeatId = new BookingShowSeatMapByShowSeatIdResponse(bookingShowSeatService.findByRoundIdAndShowSeatIds(roundId, showSeatIds));

        return showSeats.stream()
                .map(showSeat -> new ShowSeatResponse(
                        showSeat,
                        seatMapBySeatId.getById(showSeat.getSeatId()),
                        showSeatGradeMapById.getById(showSeat.getShowSeatGradeId()),
                        bookingShowSeatMapByShowSeatId.notExistsByShowSeatId(showSeat.getShowSeatId())
                        ))
                .collect(Collectors.toList());
    }
}
