package com.culture.ticketing.show.infra

import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShowRepositoryCustomTest extends Specification {

    @Autowired
    private ShowRepository showRepository;

    def "전체_공연_목록_조회_테스트_특정한_아이디보다_크고_사이즈_제한"() {

        given:
        List<Show> shows = List.of(
                ShowFixtures.createShow(1L),
                ShowFixtures.createShow(2L),
                ShowFixtures.createShow(3L),
                ShowFixtures.createShow(4L),
                ShowFixtures.createShow(5L)
        );
        showRepository.saveAll(shows);

        when:
        List<Show> foundShows = showRepository.findByShowIdGreaterThanLimitAndCategory(1L, 3, null);

        then:
        foundShows.collect(show -> show.showId > 1L).size() == 3
    }

    def "카테고리별_공연_목록_조회_테스트_특정한_아이디보다_크고_사이즈_제한"() {

        given:
        List<Show> shows = List.of(
                ShowFixtures.createShow(1L, Category.CONCERT),
                ShowFixtures.createShow(2L, Category.CONCERT),
                ShowFixtures.createShow(3L, Category.MUSICAL),
                ShowFixtures.createShow(4L, Category.CONCERT),
                ShowFixtures.createShow(5L, Category.CLASSIC)
        );
        showRepository.saveAll(shows);

        when:
        List<Show> foundShows = showRepository.findByShowIdGreaterThanLimitAndCategory(1L, 3, Category.CONCERT);

        then:
        foundShows.collect(show -> show.showId > 1L && show.category == Category.CONCERT).size() == 2
    }
}
