package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowAreaGradeRepository extends JpaRepository<ShowAreaGrade, Long> {

    List<ShowAreaGrade> findByShowId(Long showId);
}
