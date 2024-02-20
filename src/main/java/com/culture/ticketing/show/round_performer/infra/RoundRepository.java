package com.culture.ticketing.show.round_performer.infra;

import com.culture.ticketing.show.round_performer.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long>, RoundRepositoryCustom {

    List<Round> findByShowId(Long showId);
}
