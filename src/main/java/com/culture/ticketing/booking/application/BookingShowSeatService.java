package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowSeatsMapByRoundIdResponse;
import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.booking.infra.BookingShowSeatRepository;
import com.culture.ticketing.show.show_seat.application.ShowSeatService;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;
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
    public int getTotalPriceByShowSeatIds(Set<Long> showSeatIds) {
        return showSeatService.getTotalPriceByShowSeatIds(showSeatIds);
    }

    @Transactional(readOnly = true)
    public boolean hasAlreadyBookingShowSeatsByRoundId(Long roundId, Set<Long> showSeatIds) {
        return bookingShowSeatRepository.existsAlreadyBookingShowSeatsInRound(showSeatIds, roundId);
    }

    @Transactional(readOnly = true)
    public BookingShowSeatsMapByRoundIdResponse findBookingShowSeatsMapByRoundId(List<Long> roundIds) {

        List<BookingShowSeat> bookingShowSeats = bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdIn(roundIds);
        List<Long> showSeatIds = bookingShowSeats.stream()
                .map(BookingShowSeat::getShowSeatId)
                .collect(Collectors.toList());

        List<ShowSeat> showSeats = showSeatService.findByIds(showSeatIds);

        return new BookingShowSeatsMapByRoundIdResponse(bookingShowSeats, showSeats);
    }
}
