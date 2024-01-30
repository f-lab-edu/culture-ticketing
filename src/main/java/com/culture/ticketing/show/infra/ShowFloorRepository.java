package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShowFloorRepository extends JpaRepository<ShowFloor, Long> {
    List<ShowFloor> findByShowSeatGradeIdIn(Collection<Long> showSeatGradeIds);

    List<ShowFloor> findByShowId(Long showId);
}
