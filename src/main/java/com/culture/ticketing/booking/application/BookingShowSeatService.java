package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowSeatsResponse;
import com.culture.ticketing.booking.application.dto.RoundsShowSeatCountsResponse;
import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.infra.BookingShowSeatRepository;
import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse;
import com.culture.ticketing.show.domain.ShowSeat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingShowSeatService {

    private final BookingShowSeatRepository bookingShowSeatRepository;
    private final ShowSeatService showSeatService;

    public BookingShowSeatService(BookingShowSeatRepository bookingShowSeatRepository, ShowSeatService showSeatService) {
        this.bookingShowSeatRepository = bookingShowSeatRepository;
        this.showSeatService = showSeatService;
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowIdAndShowSeatIds(Set<Long> showSeatIds) {
        return showSeatService.getTotalPriceByShowSeatIds(showSeatIds);
    }

    @Transactional(readOnly = true)
    public boolean hasAlreadyBookingShowSeatsByRoundId(Long roundId, Set<Long> showSeatIds) {
        return bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds).size() > 0;
    }

    @Transactional(readOnly = true)
    public BookingShowSeatsResponse findByRoundIdAndShowSeatIds(Long roundId, List<Long> showSeatIds) {

        return new BookingShowSeatsResponse(bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds));
    }

    @Transactional(readOnly = true)
    public RoundsShowSeatCountsResponse findRoundsShowSeatCounts(Long showId, List<Long> roundIds) {

        ShowSeatCountsResponse showSeatCounts = showSeatService.findShowSeatCountsByShowId(showId);

        List<BookingShowSeat> bookingShowSeats = bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdIn(roundIds);
        List<Long> showSeatIds = bookingShowSeats.stream()
                .map(BookingShowSeat::getShowSeatId)
                .collect(Collectors.toList());
        List<ShowSeat> showSeatsInBooking = showSeatService.findByIds(showSeatIds);

        return new RoundsShowSeatCountsResponse(roundIds, showSeatCounts, bookingShowSeats, showSeatsInBooking);
    }
}
