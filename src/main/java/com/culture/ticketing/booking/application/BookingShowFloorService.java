package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.infra.BookingShowFloorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingShowFloorService {

    private final BookingShowFloorRepository bookingShowFloorRepository;

    public BookingShowFloorService(BookingShowFloorRepository bookingShowFloorRepository) {
        this.bookingShowFloorRepository = bookingShowFloorRepository;
    }

    @Transactional
    public void createBookingShowFloors(List<BookingShowFloorSaveRequest> showFloors, Long bookingId) {

        List<BookingShowFloor> bookingShowFloors = showFloors.stream()
                .map(showFloor -> BookingShowFloor.builder()
                        .bookingId(bookingId)
                        .showFloorId(showFloor.getShowFloorId())
                        .entryOrder(showFloor.getEntryOrder())
                        .build())
                .collect(Collectors.toList());
        bookingShowFloorRepository.saveAll(bookingShowFloors);
    }
}
