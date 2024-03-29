package com.culture.ticketing.show.infra

import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShowRepositoryCustomTest extends Specification {

    @Autowired
    private ShowRepository showRepository;

    @BeforeEach
    void setup() {
        showRepository.deleteAll();
    }

    def "전체 공연 목록 조회 테스트 특정한 아이디보다 크고 사이즈 제한"() {

        given:
        showRepository.saveAll([
                ShowFixtures.createShow(showId: 1L),
                ShowFixtures.createShow(showId: 2L),
                ShowFixtures.createShow(showId: 3L),
                ShowFixtures.createShow(showId: 4L),
                ShowFixtures.createShow(showId: 5L)
        ]);

        when:
        List<Show> foundShows = showRepository.findByShowIdGreaterThanLimitAndCategory(1L, 3, null);

        then:
        foundShows.collect(show -> show.showId > 1L).size() == 3
    }

    def "카테고리별 공연 목록 조회 테스트 특정한 아이디보다 크고 사이즈 제한"() {

        given:
        List<Show> shows = [
                ShowFixtures.createShow(category: Category.CONCERT),
                ShowFixtures.createShow(category: Category.CONCERT),
                ShowFixtures.createShow(category: Category.MUSICAL),
                ShowFixtures.createShow(category: Category.CONCERT),
                ShowFixtures.createShow(category: Category.CLASSIC)
        ]
        showRepository.saveAll(shows);

        when:
        List<Show> foundShows = showRepository.findByShowIdGreaterThanLimitAndCategory(shows.get(0).showId, 3, Category.CONCERT);

        then:
        foundShows.size() == 2
        foundShows.collect(show -> show.showId > shows.get(0).showId && show.category == Category.CONCERT).size() == 2
    }
}
