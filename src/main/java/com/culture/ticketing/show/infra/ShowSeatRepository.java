package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
}
