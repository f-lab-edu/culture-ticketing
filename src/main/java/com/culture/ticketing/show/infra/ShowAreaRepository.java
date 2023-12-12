package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowAreaRepository extends JpaRepository<ShowArea, Long> {

    List<ShowArea> findByShowId(Long showId);
}
