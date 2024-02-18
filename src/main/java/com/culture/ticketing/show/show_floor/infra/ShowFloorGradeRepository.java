package com.culture.ticketing.show.show_floor.infra;

import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowFloorGradeRepository extends JpaRepository<ShowFloorGrade, Long> {

    List<ShowFloorGrade> findByShowId(Long showId);
}
