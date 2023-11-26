package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.domain.ShowSchedule;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ShowScheduleRepositoryCustom {

    Optional<ShowSchedule> findByShowAndDuplicatedShowScheduleDateTime(Show show, LocalDateTime showScheduleDateTime);
}
