package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingStatus;

import java.util.Set;

public interface BookingShowFloorRepositoryCustom {

    boolean existsByShowFloorsInAndBooking_RoundIdAndBooking_BookingStatus(Set<BookingShowFloorSaveRequest> showFloors, Long roundId, BookingStatus bookingStatus);
}
