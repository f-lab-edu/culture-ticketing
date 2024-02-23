package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowSeat;

import java.util.Collection;
import java.util.List;

public interface BookingShowSeatRepositoryCustom {

    List<BookingShowSeat> findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(Long roundId, Collection<Long> showSeatIds);

    List<BookingShowSeat> findSuccessBookingShowSeatsByRoundIdIn(Collection<Long> roundIds);
}
