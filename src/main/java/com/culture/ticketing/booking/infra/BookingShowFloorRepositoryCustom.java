package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingShowFloor;

import java.util.Collection;
import java.util.List;

public interface BookingShowFloorRepositoryCustom {

    boolean existsAlreadyBookingShowFloorsInRound(Collection<BookingShowFloorSaveRequest> showFloors, Long roundId);

    List<BookingShowFloor> findSuccessBookingShowFloorsByRoundIdIn(Collection<Long> roundIds);
}
