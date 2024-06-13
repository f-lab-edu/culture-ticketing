package com.culture.ticketing.show.infra

import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.domain.ShowFilter
import com.culture.ticketing.show.domain.ShowOrderBy
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureDataRedis
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
        List<Show> foundShows = showRepository.searchShowsWithPaging(1L, 3, new ShowFilter(null, null), ShowOrderBy.NEWEST);

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
        List<Show> foundShows = showRepository.searchShowsWithPaging(shows.get(0).showId, 3, new ShowFilter(Category.CONCERT, null), ShowOrderBy.NEWEST);

        then:
        foundShows.size() == 2
        foundShows.collect(show -> show.showId > shows.get(0).showId && show.category == Category.CONCERT).size() == 2
    }

    def "검색어 포함 공연 목록 조회 테스트 특정한 아이디보다 크고 사이즈 제한"() {

        given:
        List<Show> shows = [
                ShowFixtures.createShow(showName: "공연1"),
                ShowFixtures.createShow(showName: "테스트1"),
                ShowFixtures.createShow(showName: "공연2"),
                ShowFixtures.createShow(showName: "테스트2"),
                ShowFixtures.createShow(showName: "공연3"),
        ]
        showRepository.saveAll(shows);

        when:
        List<Show> foundShows = showRepository.searchShowsWithPaging(shows.get(0).showId, 3, new ShowFilter(null, "공연"), ShowOrderBy.NEWEST);

        then:
        foundShows.size() == 2
        foundShows.collect(show -> show.showId > shows.get(0).showId && show.showName.contains("공연")).size() == 2
    }

    def "공연 목록 조회 정렬 테스트 특정한 아이디보다 크고 사이즈 제한"() {

        given:
        List<Show> shows = [
                ShowFixtures.createShow(showName: "공연3"),
                ShowFixtures.createShow(showName: "공연2"),
                ShowFixtures.createShow(showName: "공연1"),
        ]
        showRepository.saveAll(shows);

        when:
        List<Show> foundShows = showRepository.searchShowsWithPaging(shows.get(0).showId, 3, new ShowFilter(null, null), ShowOrderBy.SHOW_NAME_ASC);

        then:
        foundShows.size() == 2
        foundShows.get(0).showName == "공연1"
        foundShows.get(1).showName == "공연2"
    }

    def "예약 시작 시간까지 1시간 남은 공연 목록 조회"() {

        given:
        List<Show> shows = [
                ShowFixtures.createShow(category: Category.CONCERT,
                        bookingStartDateTime: LocalDateTime.of(2024, 1, 1, 20, 0, 0),
                        bookingEndDateTime: LocalDateTime.of(2024, 1, 1, 22, 0, 0)
                ),
                ShowFixtures.createShow(category: Category.CONCERT,
                        bookingStartDateTime: LocalDateTime.of(2024, 1, 1, 19, 0, 0),
                        bookingEndDateTime: LocalDateTime.of(2024, 1, 1, 22, 0, 0)
                ),
                ShowFixtures.createShow(category: Category.CONCERT,
                        bookingStartDateTime: LocalDateTime.of(2024, 1, 1, 20, 1, 0),
                        bookingEndDateTime: LocalDateTime.of(2024, 1, 1, 22, 0, 0)
                ),
                ShowFixtures.createShow(category: Category.CONCERT,
                        bookingStartDateTime: LocalDateTime.of(2024, 1, 1, 20, 0, 59),
                        bookingEndDateTime: LocalDateTime.of(2024, 1, 1, 22, 0, 0)
                ),
        ]
        showRepository.saveAll(shows);

        when:
        List<Show> foundShows = showRepository.findByBookingStartDateTimeLeftAnHour(LocalDateTime.of(2024, 1, 1, 19, 0, 0), 10, 0);

        then:
        foundShows.size() == 2
        foundShows.showId == [shows.get(0).showId, shows.get(3).showId]
    }
}
