package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.RoundPerformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundPerformerRepository extends JpaRepository<RoundPerformer, Long> {
}
