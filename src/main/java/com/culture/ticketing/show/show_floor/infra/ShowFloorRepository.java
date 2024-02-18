package com.culture.ticketing.show.show_floor.infra;

import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShowFloorRepository extends JpaRepository<ShowFloor, Long> {

    List<ShowFloor> findByShowFloorGradeIdIn(Collection<Long> showFloorGradeIds);
}
