package com.culture.ticketing.place.infra;

import com.culture.ticketing.place.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findByPlaceIdAndCoordinateXAndCoordinateY(Long placeId, int coordinateX, int coordinateY);

    Optional<Seat> findByPlaceIdAndAreaAndSeatRowAndSeatNumber(Long placeId, String area, int seatRow, int seatNumber);
}
