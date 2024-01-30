package com.culture.ticketing.place

import com.culture.ticketing.place.domain.Seat

class SeatFixtures {

    static Seat creatSeat(Map map = [:]) {
        return Seat.builder()
                .seatId(map.getOrDefault("seatId", 1L) as Long)
                .seatRow(map.getOrDefault("seatRow", 1) as Integer)
                .seatNumber(map.getOrDefault("seatNumber", 1) as Integer)
                .areaId(map.getOrDefault("areaId", 1L) as Long)
                .build();
    }
}
