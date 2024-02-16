package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.booking.infra.BookingShowSeatRepository;
import com.culture.ticketing.show.application.ShowSeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class BookingShowSeatService {

    private final BookingShowSeatRepository bookingShowSeatRepository;
    private final ShowSeatService showSeatService;

    public BookingShowSeatService(BookingShowSeatRepository bookingShowSeatRepository, ShowSeatService showSeatService) {
        this.bookingShowSeatRepository = bookingShowSeatRepository;
        this.showSeatService = showSeatService;
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowSeatIds(Set<Long> showSeatIds) {
        return showSeatService.getTotalPriceByShowSeatIds(showSeatIds);
    }

    @Transactional(readOnly = true)
    public boolean hasAlreadyBookingShowSeatsByRoundId(Long roundId, Set<Long> showSeatIds) {
        return bookingShowSeatRepository.existsByShowSeatIdInAndBooking_RoundIdAndBooking_BookingStatus(showSeatIds, roundId, BookingStatus.SUCCESS);
    }
}
