package com.culture.ticketing.show.show_seat.infra;

import com.culture.ticketing.show.show_seat.domain.ShowSeat;

import java.util.List;

public interface ShowSeatRepositoryCustom {

    List<ShowSeat> findByAreaId(Long areaId);
}
