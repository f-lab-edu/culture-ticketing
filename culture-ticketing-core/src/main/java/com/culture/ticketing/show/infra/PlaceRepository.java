package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
}
