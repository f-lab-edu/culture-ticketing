package com.culture.ticketing.show.application

import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.infra.ShowRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ShowServiceTest extends Specification {

    @SpringBean
    private ShowRepository showRepository = Mock();
    @SpringBean
    private PlaceService placeService = Mock();
    private ShowService showService = new ShowService(showRepository, placeService);

    def "공연 아이디 값으로 공연 존재 여부 확인"() {

        given:
        Long showId = 1L;
        showRepository.existsById(showId) >> true

        when:
        boolean response = showService.notExistsById(showId);

        then:
        !response
    }
}
