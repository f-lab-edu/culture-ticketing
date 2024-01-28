package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowSeatGradeIdIn(Collection<Long> showSeatGradeId);
}
