package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.RoundPerformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundPerformerRepository extends JpaRepository<RoundPerformer, Long> {

    List<RoundPerformer> findByRoundIdIn(List<Long> roundIds);
}
