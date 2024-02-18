package com.culture.ticketing.show.round_performer.infra;

import com.culture.ticketing.show.round_performer.domain.RoundPerformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoundPerformerRepository extends JpaRepository<RoundPerformer, Long> {

    List<RoundPerformer> findByRoundIdIn(Collection<Long> roundIds);
}
