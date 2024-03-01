package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    Optional<ShowSeat> findByShowAreaIdAndShowSeatRowAndShowSeatNumber(Long showAreaId, String showSeatRow, int showSeatNumber);

    List<ShowSeat> findByShowAreaId(Long showAreaId);

    List<ShowSeat> findByShowAreaIdIn(Collection<Long> showAreaIds);
}
