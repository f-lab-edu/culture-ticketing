package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingShowSeatRepository extends JpaRepository<BookingShowSeat, Long> {

    boolean existsByShowSeatIdInAndBooking_RoundIdAndBooking_BookingStatus(Set<Long> showSeatIds, Long roundId, BookingStatus bookingStatus);

    List<BookingShowSeat> findByBooking_RoundIdInAndBooking_BookingStatus(Collection<Long> roundIds, BookingStatus bookingStatus);
}
