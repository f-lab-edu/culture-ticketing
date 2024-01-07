package com.culture.ticketing.show.application

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.place.domain.Place
import com.culture.ticketing.place.exception.PlaceNotFoundException
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.application.dto.ShowSaveRequest
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.infra.ShowRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

import java.time.LocalDate

class ShowServiceTest extends Specification {

    @SpringBean
    private ShowRepository showRepository = Mock();
    @SpringBean
    private PlaceService placeService = Mock();
    private ShowService showService = new ShowService(showRepository, placeService);

    def "공연_생성_시_카테고리가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_관람_제한가가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_장소_아이디_값이_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_시작_날짜가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_종료_날짜가_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_이름이_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_공연_이름이_빈_값인_경우_예외_발생"() {

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

    def "공연_생성_시_포스터_이미지_url_이_null_인_경우_예외_발생"() {

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

    def "공연_생성_시_포스터_이미지_url_이_빈_값인_경우_예외_발생"() {

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

    def "공연_생성_시_러닝_시간이_0이하_인_경우_예외_발생"() {

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

    def "공연_생성_시_장소_아이디_값에_해당하는_장소가_존재하지_않을_경우_예외_발생"() {

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

    def "공연_아이디_값으로_공연_존재_여부_확인"() {

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

    def "카테고리_별_공연_목록_조회"() {

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

    def "공연_목록의_장소_아이디에_해당하는_장소_없는_경우_예외_발생"() {

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

    def "공연_아이디로_공연_조회_시_없는_경우_예외_발생"() {
        given:
        showRepository.findById(1L) >> Optional.empty()

        when:
        showService.findShowById(1L);

        then:
        def e = thrown(ShowNotFoundException.class);
        e.message == "존재하지 않는 공연입니다. (showId = 1)"
    }
}
