package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ShowScheduleRepository extends JpaRepository<ShowSchedule, Long> {

    Optional<ShowSchedule> findByShowIdAndShowScheduleDateAndShowScheduleTime(Long showId, LocalDate showScheduleDate, LocalTime showScheduleTime);
}
