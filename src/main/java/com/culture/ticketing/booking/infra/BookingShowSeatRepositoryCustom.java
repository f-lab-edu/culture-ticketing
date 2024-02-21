package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowSeat;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookingShowSeatRepositoryCustom {

    List<BookingShowSeat> findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(Long roundId, Set<Long> showSeatIds);

    List<BookingShowSeat> findSuccessBookingShowSeatsByRoundIdIn(Collection<Long> roundIds);
}
