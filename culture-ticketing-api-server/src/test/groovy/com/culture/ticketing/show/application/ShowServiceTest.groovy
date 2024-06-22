package com.culture.ticketing.show.application

import com.culture.ticketing.show.PlaceFixtures
import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.domain.ShowFilter
import com.culture.ticketing.show.domain.ShowOrderBy
import com.culture.ticketing.show.infra.ShowRepository
import com.culture.ticketing.show.exception.PlaceNotFoundException
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.application.dto.ShowSaveRequest
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.exception.ShowNotFoundException
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class ShowServiceTest extends Specification {

    private ShowRepository showRepository = Mock();
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
                .bookingStartDateTime(LocalDateTime.of(2023, 12, 25, 20, 0, 0))
                .bookingEndDateTime(LocalDateTime.of(2023, 12, 31, 23, 59, 59))
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        1 * showRepository.save(_) >> { args ->

            def savedShow = args.get(0) as Show

            savedShow.category == Category.CONCERT
            savedShow.showName == "테스트"
            savedShow.ageRestriction == AgeRestriction.ALL
            savedShow.runningTime == 120
            savedShow.posterImgUrl == "http://abc.jpg"
            savedShow.showStartDate == LocalDate.of(2024, 1, 1)
            savedShow.showEndDate == LocalDate.of(2024, 5, 31)
            savedShow.placeId == 1L
        }
    }

    def "공연 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(category)
                .showName("테스트")
                .ageRestriction(ageRestriction)
                .runningTime(120)
                .posterImgUrl("http://abc.jpg")
                .showStartDate(showStartDate)
                .showEndDate(showEndDate)
                .placeId(placeId)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        category         | ageRestriction     | placeId | showStartDate            | showEndDate               || expected
        null             | AgeRestriction.ALL | 1L      | LocalDate.of(2024, 1, 1) | LocalDate.of(2024, 5, 31) || "공연 카테고리를 입력해주세요."
        Category.CONCERT | null               | 1L      | LocalDate.of(2024, 1, 1) | LocalDate.of(2024, 5, 31) || "공연 관람 제한가를 입력해주세요."
        Category.CONCERT | AgeRestriction.ALL | null    | LocalDate.of(2024, 1, 1) | LocalDate.of(2024, 5, 31) || "공연 장소 아이디를 입력해주세요."
        Category.CONCERT | AgeRestriction.ALL | 1L      | null                     | LocalDate.of(2024, 5, 31) || "공연 시작 날짜를 입력해주세요."
        Category.CONCERT | AgeRestriction.ALL | 1L      | LocalDate.of(2024, 1, 1) | null                      || "공연 종료 날짜를 입력해주세요."
    }


    def "공연 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowSaveRequest request = ShowSaveRequest.builder()
                .category(Category.CONCERT)
                .showName(showName)
                .ageRestriction(AgeRestriction.ALL)
                .runningTime(runningTime)
                .posterImgUrl(posterImgUrl)
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 5, 31))
                .placeId(1L)
                .build();

        when:
        showService.createShow(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showName | posterImgUrl     | runningTime || expected
        null     | "http://abc.jpg" | 120         || "공연 이름을 입력해주세요."
        ""       | "http://abc.jpg" | 120         || "공연 이름을 입력해주세요."
        "테스트"    | null             | 120         || "공연 포스터 이미지 url을 입력해주세요."
        "테스트"    | ""               | 120         || "공연 포스터 이미지 url을 입력해주세요."
        "테스트"    | "http://abc.jpg" | 0           || "공연 러닝 시간을 0 초과로 입력해주세요."
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

    def "전체 공연 목록 조회"() {

        given:
        showRepository.searchShowsWithPaging(1L, 3, _, null) >> [
                ShowFixtures.createShow(showId: 2L, placeId: 1L),
                ShowFixtures.createShow(showId: 3L, placeId: 2L),
                ShowFixtures.createShow(showId: 4L, placeId: 2L)
        ]
        placeService.findPlacesByIds([1L, 2L, 2L]) >> [
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 2L)),
        ]
        when:
        List<ShowResponse> response = showService.searchShows(1L, 3, null, null, null);

        then:
        response.collect(show -> show.showId > 1L).size() == 3
    }

    def "카테고리 별 공연 목록 조회"() {

        given:
        showRepository.searchShowsWithPaging(1L, 3, _, null) >> [
                ShowFixtures.createShow(showId: 3L, category: Category.CONCERT, placeId: 1L),
                ShowFixtures.createShow(showId: 5L, category: Category.CONCERT, placeId: 2L)
        ]

        placeService.findPlacesByIds([1L, 2L]) >> [
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 2L))
        ]

        when:
        List<ShowResponse> response = showService.searchShows(1L, 3, Category.CONCERT, null, null);

        then:
        response.collect(show -> show.showId > 1L && show.categoryName == Category.CONCERT.getCategoryName()).size() == 2
    }

    def "검색어로 공연 목록 조회"() {

        given:
        showRepository.searchShowsWithPaging(1L, 3, _, null) >> [
                ShowFixtures.createShow(showId: 3L, placeId: 1L, showName: "공연1"),
                ShowFixtures.createShow(showId: 5L, placeId: 2L, showName: "공연2")
        ]

        placeService.findPlacesByIds([1L, 2L]) >> [
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 2L))
        ]

        when:
        List<ShowResponse> response = showService.searchShows(1L, 3, null, "공연", null);

        then:
        response.collect(show -> show.showId > 1L && show.showName.contains("공연")).size() == 2
    }

    def "공연 목록 조회 정렬"() {

        given:
        showRepository.searchShowsWithPaging(1L, 3, _, ShowOrderBy.SHOW_NAME_ASC) >> [
                ShowFixtures.createShow(showId: 3L, placeId: 1L, showName: "공연1"),
                ShowFixtures.createShow(showId: 5L, placeId: 2L, showName: "공연2")
        ]

        placeService.findPlacesByIds([1L, 2L]) >> [
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: 2L))
        ]

        when:
        List<ShowResponse> response = showService.searchShows(1L, 3, null, null, ShowOrderBy.SHOW_NAME_ASC);

        then:
        response.collect(show -> show.showId > 1L).size() == 2
    }

    def "공연 아이디로 공연 조회 시 없는 경우 예외 발생"() {

        given:
        Long showId = 1L;
        showRepository.findById(showId) >> Optional.empty()

        when:
        showService.findShowById(showId);

        then:
        def e = thrown(ShowNotFoundException.class);
        e.message == String.format("존재하지 않는 공연입니다. (showId = %d)", showId)
    }

    def "공연 아이디로 공연 조회 성공"() {

        given:
        Long showId = 1L;
        showRepository.findById(showId) >> Optional.of(ShowFixtures.createShow(showId: 1L, placeId: 1L));
        placeService.findPlaceById(1L) >> new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L));

        when:
        ShowResponse response = showService.findShowById(showId);

        then:
        response.showId == 1L
        response.place.placeId == 1L
    }
}
