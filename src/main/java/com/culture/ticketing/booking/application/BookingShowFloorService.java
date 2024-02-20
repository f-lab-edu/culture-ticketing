package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.application.dto.BookingShowFloorsMapByRoundIdResponse;
import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.booking.infra.BookingShowFloorRepository;
import com.culture.ticketing.show.show_floor.application.ShowFloorService;
import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingShowFloorService {

    private final BookingShowFloorRepository bookingShowFloorRepository;
    private final ShowFloorService showFloorService;

    public BookingShowFloorService(BookingShowFloorRepository bookingShowFloorRepository, ShowFloorService showFloorService) {
        this.bookingShowFloorRepository = bookingShowFloorRepository;
        this.showFloorService = showFloorService;
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowFloorIds(List<Long> showFloorIds) {
        return showFloorService.getTotalPriceByShowFloorIds(showFloorIds);
    }

    @Transactional(readOnly = true)
    public boolean hasAlreadyBookingShowFloorsByRoundId(Long roundId, Set<BookingShowFloorSaveRequest> showFloors) {
        return bookingShowFloorRepository.existsAlreadyBookingShowFloorsInRound(showFloors, roundId, BookingStatus.SUCCESS);
    }

    @Transactional(readOnly = true)
    public BookingShowFloorsMapByRoundIdResponse findBookingShowFloorsMapByRoundId(List<Long> roundIds) {

        List<BookingShowFloor> bookingShowFloors = bookingShowFloorRepository.findByBooking_RoundIdInAndBooking_BookingStatus(roundIds, BookingStatus.SUCCESS);
        List<Long> showFloorIds = bookingShowFloors.stream()
                .map(BookingShowFloor::getShowFloorId)
                .collect(Collectors.toList());

        List<ShowFloor> showFloors = showFloorService.findByIds(showFloorIds);

        return new BookingShowFloorsMapByRoundIdResponse(bookingShowFloors, showFloors);
    }
}
