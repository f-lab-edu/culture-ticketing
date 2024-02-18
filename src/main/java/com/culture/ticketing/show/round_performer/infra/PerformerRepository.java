package com.culture.ticketing.show.round_performer.infra;

import com.culture.ticketing.show.round_performer.domain.Performer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {

    List<Performer> findByShowId(Long showId);
    List<Performer> findByShowIdAndPerformerIdIn(Long showId, Collection<Long> performerIds);
}
