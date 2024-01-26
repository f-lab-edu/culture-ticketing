package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.infra.BookingShowSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingShowSeatService {

    private final BookingShowSeatRepository bookingShowSeatRepository;

    public BookingShowSeatService(BookingShowSeatRepository bookingShowSeatRepository) {
        this.bookingShowSeatRepository = bookingShowSeatRepository;
    }

    @Transactional
    public void createBookingShowSeats(List<Long> showSeatIds, Long bookingId) {

        List<BookingShowSeat> bookingShowSeats = showSeatIds.stream()
                .map(showSeatId -> BookingShowSeat.builder()
                        .bookingId(bookingId)
                        .showSeatId(showSeatId)
                        .build())
                .collect(Collectors.toList());
        bookingShowSeatRepository.saveAll(bookingShowSeats);
    }
}
