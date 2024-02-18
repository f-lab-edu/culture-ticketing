package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowFloorGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowFloorGradeRepository extends JpaRepository<ShowFloorGrade, Long> {

    List<ShowFloorGrade> findByShowId(Long showId);
}
