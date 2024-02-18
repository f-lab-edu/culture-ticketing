package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowFloorGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowFloorGradeRepository extends JpaRepository<ShowFloorGrade, Long> {
}
