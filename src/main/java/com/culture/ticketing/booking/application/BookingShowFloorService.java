package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.booking.infra.BookingShowFloorRepository;
import com.culture.ticketing.show.application.ShowFloorService;
import com.culture.ticketing.show.domain.ShowFloor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingShowFloorService {

    private final BookingShowFloorRepository bookingShowFloorRepository;
    private final ShowFloorService showFloorService;

    public BookingShowFloorService(BookingShowFloorRepository bookingShowFloorRepository, ShowFloorService showFloorService) {
        this.bookingShowFloorRepository = bookingShowFloorRepository;
        this.showFloorService = showFloorService;
    }

    @Transactional(readOnly = true)
    public int getTotalPriceByShowFloors(Set<BookingShowFloorSaveRequest> showFloors) {
        return showFloorService.getTotalPriceByShowFloorIds(showFloors.stream()
                .map(BookingShowFloorSaveRequest::getShowFloorId)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public boolean hasAlreadyBookingShowFloorsByRoundId(Long roundId, Set<BookingShowFloorSaveRequest> showFloors) {
        return bookingShowFloorRepository.existsAlreadyBookingShowFloorsInRound(showFloors, roundId, BookingStatus.SUCCESS);
    }

    @Transactional(readOnly = true)
    public Map<Long, List<ShowFloor>> findBookingShowFloorsMapByRoundId(List<Long> roundIds) {

        List<BookingShowFloor> bookingShowFloors = bookingShowFloorRepository.findByBooking_RoundIdInAndBooking_BookingStatus(roundIds, BookingStatus.SUCCESS);
        List<Long> showFloorIds = bookingShowFloors.stream()
                .map(BookingShowFloor::getShowFloorId)
                .collect(Collectors.toList());

        Map<Long, ShowFloor> showFloorMapById = showFloorService.findByIds(showFloorIds).stream()
                .collect(Collectors.toMap(ShowFloor::getShowFloorId, Function.identity()));

        return bookingShowFloors.stream()
                .collect(Collectors.groupingBy(
                        bookingShowFloor -> bookingShowFloor.getBooking().getRoundId(),
                        Collectors.mapping(bookingShowFloor -> showFloorMapById.get(bookingShowFloor.getShowFloorId()), Collectors.toList())
                ));
    }
}
