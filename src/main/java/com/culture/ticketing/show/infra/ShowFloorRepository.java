package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowFloorRepository extends JpaRepository<ShowFloor, Long> {
}
