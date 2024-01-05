package com.culture.ticketing.place

import com.culture.ticketing.place.domain.Seat

class SeatFixtures {

    static Seat creatSeat(Long seatId) {
        return Seat.builder()
                .seatId(seatId)
                .seatRow(seatId.toInteger())
                .seatNumber(seatId.toInteger())
                .areaId(seatId)
                .build();
    }
}
