package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.booking.infra.BookingShowFloorRepository;
import com.culture.ticketing.show.application.ShowFloorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int getTotalPriceByShowFloorIds(Set<BookingShowFloorSaveRequest> showFloors) {
        return showFloorService.getTotalPriceByShowFloorIds(showFloors.stream()
                .map(BookingShowFloorSaveRequest::getShowFloorId)
                .collect(Collectors.toSet()));
    }

    @Transactional(readOnly = true)
    public boolean hasAlreadyBookingShowFloorsByRoundId(Long roundId, Set<BookingShowFloorSaveRequest> showFloors) {
        return bookingShowFloorRepository.existsByShowFloorsInAndBooking_RoundIdAndBooking_BookingStatus(showFloors, roundId, BookingStatus.SUCCESS);
    }
}
