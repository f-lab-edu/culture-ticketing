package com.culture.ticketing.place

import com.culture.ticketing.place.domain.Seat

class SeatFixtures {

    static Seat creatSeat(Long seatId, int seatRow = 1, int seatNumber = 1, Long areaId = 1L) {
        return Seat.builder()
                .seatId(seatId)
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .areaId(areaId)
                .build();
    }
}
