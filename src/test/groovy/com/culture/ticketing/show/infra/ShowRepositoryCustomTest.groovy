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
        showRepository.saveAll([
                ShowFixtures.createShow(showId: 1L, category: Category.CONCERT),
                ShowFixtures.createShow(showId: 2L, category: Category.CONCERT),
                ShowFixtures.createShow(showId: 3L, category: Category.MUSICAL),
                ShowFixtures.createShow(showId: 4L, category: Category.CONCERT),
                ShowFixtures.createShow(showId: 5L, category: Category.CLASSIC)
        ]);

        when:
        List<Show> foundShows = showRepository.findByShowIdGreaterThanLimitAndCategory(1L, 3, Category.CONCERT);

        then:
        foundShows.collect(show -> show.showId > 1L && show.category == Category.CONCERT).size() == 2
    }
}
