package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.booking.infra.BookingShowSeatRepository;
import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.domain.ShowSeat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
        return bookingShowSeatRepository.existsByShowSeatIdInAndBooking_RoundIdAndBooking_BookingStatus(showSeatIds, roundId, BookingStatus.SUCCESS);
    }

    @Transactional(readOnly = true)
    public Map<Long, List<ShowSeat>> findBookingShowSeatsMapByRoundId(List<Long> roundIds) {

        List<BookingShowSeat> bookingShowSeats = bookingShowSeatRepository.findByBooking_RoundIdInAndBooking_BookingStatus(roundIds, BookingStatus.SUCCESS);
        List<Long> showSeatIds = bookingShowSeats.stream()
                .map(BookingShowSeat::getShowSeatId)
                .collect(Collectors.toList());

        Map<Long, ShowSeat> showSeatMapById = showSeatService.findByIds(showSeatIds).stream()
                .collect(Collectors.toMap(ShowSeat::getShowSeatId, Function.identity()));

        return bookingShowSeats.stream()
                .collect(Collectors.groupingBy(
                        bookingShowSeat -> bookingShowSeat.getBooking().getRoundId(),
                        Collectors.mapping(bookingShowSeat -> showSeatMapById.get(bookingShowSeat.getShowSeatId()), Collectors.toList())
                ));
    }
}
