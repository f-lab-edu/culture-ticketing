package com.culture.ticketing.show.show_seat.infra;

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatGradeRepository extends JpaRepository<ShowSeatGrade, Long> {

    List<ShowSeatGrade> findByShowId(Long showId);
}
