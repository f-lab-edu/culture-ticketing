package com.culture.ticketing.place.infra;

import com.culture.ticketing.place.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    List<Area> findByPlaceId(Long placeId);
}
