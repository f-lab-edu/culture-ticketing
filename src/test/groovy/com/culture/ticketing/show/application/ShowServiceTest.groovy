package com.culture.ticketing.show.application

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.infra.ShowRepository
import com.culture.ticketing.place.domain.Place
import com.culture.ticketing.place.exception.PlaceNotFoundException
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.application.dto.ShowSaveRequest
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.exception.ShowNotFoundException
import org.spockframework.spring.SpringBean
import spock.lang.Specification

import java.time.LocalDate

class ShowServiceTest extends Specification {

    @SpringBean
    private ShowRepository showRepository = Mock();
    @SpringBean
    private PlaceService placeService = Mock();
    private ShowService showService = new ShowService(showRepository, placeService);

    def "공연 생성 성공"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        1 * showRepository.save(_)
    }

    def "공연 생성 시 카테고리가 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(null)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 카테고리를 입력해주세요."
    }

    def "공연 생성 시 관람 제한가가 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(null)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 관람 제한가를 입력해주세요."
    }

    def "공연 생성 시 장소 아이디 값이 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(null)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 장소 아이디를 입력해주세요."
    }

    def "공연 생성 시 공연 시작 날짜가 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(null)
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 시작 날짜를 입력해주세요."
    }

    def "공연 생성 시 공연 종료 날짜가 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(null)
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 종료 날짜를 입력해주세요."
    }

    def "공연 생성 시 공연 이름이 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName(null)
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 이름을 입력해주세요."
    }

    def "공연 생성 시 공연 이름이 빈 값인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 이름을 입력해주세요."
    }

    def "공연 생성 시 포스터 이미지 url 이 null 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl(null)
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 포스터 이미지 url을 입력해주세요."
    }

    def "공연 생성 시 포스터 이미지 url 이 빈 값인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 포스터 이미지 url을 입력해주세요."
    }

    def "공연 생성 시 러닝 시간이 0이하 인 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(0)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 러닝 시간을 0 초과로 입력해주세요."
    }

    def "공연 생성 시 장소 아이디 값에 해당하는 장소가 존재하지 않을 경우 예외 발생"() {

        given:
        Long placeId = 1L;
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName("테스트")
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(placeId)
                .build();
        placeService.notExistsById(placeId) >> true

        when:
        showService.createShow(request);

        then:
        def e = thrown(PlaceNotFoundException.class)
        e.message == String.format("존재하지 않는 장소입니다. (placeId = %d)", placeId)
    }

    def "공연 아이디 값으로 공연 존재 여부 확인"() {

        given:
        Long showId = 1L;
        showRepository.existsById(showId) >> true

        when:
        boolean response = showService.notExistsById(showId);

        then:
        !response
    }

    def "전체_공연_목록_조회"() {

        given:
        List<Show> shows = List.of(
                ShowFixtures.createShow(1L),
                ShowFixtures.createShow(2L),
                ShowFixtures.createShow(3L),
                ShowFixtures.createShow(4L),
                ShowFixtures.createShow(5L)
        );
        List<Show> foundShows = shows.subList(1, 4);
        showRepository.findByShowIdGreaterThanLimitAndCategory(1L, 3, null) >> foundShows
        List<Long> placeIds = foundShows.collect(show -> show.placeId);
        placeService.findPlacesByIds(placeIds) >> placeIds.toSet().collect(placeId -> PlaceFixtures.createPlace(placeId))

        when:
        List<ShowResponse> response = showService.findShows(1L, 3, null);

        then:
        response.collect(show -> show.showId > 1L).size() == 3
    }

    def "카테고리 별 공연 목록 조회"() {

        given:
        List<Show> shows = List.of(
                ShowFixtures.createShow(1L, Category.CONCERT),
                ShowFixtures.createShow(2L, Category.MUSICAL),
                ShowFixtures.createShow(3L, Category.CONCERT),
                ShowFixtures.createShow(4L, Category.CLASSIC),
                ShowFixtures.createShow(5L, Category.CONCERT)
        );
        List<Show> foundShows = List.of(shows.get(2), shows.get(4));
        showRepository.findByShowIdGreaterThanLimitAndCategory(1L, 3, Category.CONCERT) >> foundShows
        List<Long> placeIds = foundShows.collect(show -> show.placeId);
        placeService.findPlacesByIds(placeIds) >> placeIds.toSet().collect(placeId -> PlaceFixtures.createPlace(placeId))

        when:
        List<ShowResponse> response = showService.findShows(1L, 3, Category.CONCERT);

        then:
        response.collect(show -> show.showId > 1L && show.categoryName == Category.CONCERT.getCategoryName()).size() == 2
    }

    def "공연 목록의 장소 아이디에 해당하는 장소 없는 경우 예외 발생"() {

        given:
        List<Show> shows = List.of(
                ShowFixtures.createShow(1L),
                ShowFixtures.createShow(2L),
                ShowFixtures.createShow(3L),
                ShowFixtures.createShow(4L),
                ShowFixtures.createShow(5L)
        );
        Map<Long, Place> placeMapByPlaceId = new HashMap<>();

        when:
        showService.checkPlaceExistInShows(shows, placeMapByPlaceId);

        then:
        def e = thrown(PlaceNotFoundException.class);
        e.message == "존재하지 않는 장소입니다. (placeId = 1)"

    }

    def "공연 아이디로 공연 조회 시 없는 경우 예외 발생"() {
        given:
        showRepository.findById(1L) >> Optional.empty()

        when:
        showService.findShowById(1L);

        then:
        def e = thrown(ShowNotFoundException.class);
        e.message == "존재하지 않는 공연입니다. (showId = 1)"
    }
}
