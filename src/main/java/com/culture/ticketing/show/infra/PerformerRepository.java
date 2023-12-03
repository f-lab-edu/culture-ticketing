package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Performer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {

    List<Performer> findByShowId(Long showId);
}
