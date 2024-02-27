package com.culture.ticketing.show.show_seat.infra;

import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long>, ShowSeatRepositoryCustom {

    List<ShowSeat> findByShowSeatGradeIdIn(Collection<Long> showSeatGradeId);
}
