package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatGradeRepository extends JpaRepository<ShowSeatGrade, Long> {
}
