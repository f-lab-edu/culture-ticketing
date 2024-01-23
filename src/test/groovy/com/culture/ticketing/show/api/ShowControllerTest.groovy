package com.culture.ticketing.show.api

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.application.ShowFacadeService
import com.culture.ticketing.show.application.ShowService
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.application.dto.ShowSaveRequest
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShowController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShowControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private ShowService showService = Mock();
    @SpringBean
    private ShowFacadeService showFacadeService = Mock();

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 카테고리가 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 관람가가 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 공연 이름이 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 공연 이름이 빈 값인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 러닝 시간이 0이하인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 포스터 이미지 url 이 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 포스터 이미지 url 이 빈 값인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 공연 시작 날짜가 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 공연 종료 날짜가 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 생성 시 장소 아이디 값이 null 인 경우 400 에러"() {

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

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
    }

    def "전체 공연 목록 조회 성공"() {

        given:
        List<ShowResponse> shows = List.of(
                ShowResponse.from(ShowFixtures.createShow(1L), PlaceFixtures.createPlace(1L)),
                ShowResponse.from(ShowFixtures.createShow(2L), PlaceFixtures.createPlace(1L)),
                ShowResponse.from(ShowFixtures.createShow(3L), PlaceFixtures.createPlace(1L)),
                ShowResponse.from(ShowFixtures.createShow(4L), PlaceFixtures.createPlace(1L))
        );
        Long offset = shows.get(0).showId;
        showService.findShows(offset, 3, null) >> shows.subList(1, 4)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shows")
                .param("offset", offset.toString())
                .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$[0].showId", Matchers.greaterThan(offset.toInteger())))
                .andExpect(jsonPath("\$[1].showId", Matchers.greaterThan(offset.toInteger())))
                .andExpect(jsonPath("\$[2].showId", Matchers.greaterThan(offset.toInteger())))
                .andDo(MockMvcResultHandlers.print())
    }

    def "카테고리 별 공연 목록 조회"() {

        given:
        List<ShowResponse> shows = List.of(
                ShowResponse.from(ShowFixtures.createShow(1L, Category.CONCERT), PlaceFixtures.createPlace(1L)),
                ShowResponse.from(ShowFixtures.createShow(2L, Category.MUSICAL), PlaceFixtures.createPlace(1L)),
                ShowResponse.from(ShowFixtures.createShow(3L, Category.CONCERT), PlaceFixtures.createPlace(1L)),
                ShowResponse.from(ShowFixtures.createShow(4L, Category.CLASSIC), PlaceFixtures.createPlace(1L))
        );
        Long offset = 0;
        Category category = Category.CONCERT;
        showService.findShows(offset, 3, category) >> List.of(shows.get(0), shows.get(2));

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shows")
                .param("offset", offset.toString())
                .param("size", "3")
                .param("category", category.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$[0].showId", Matchers.greaterThan(offset.toInteger())))
                .andExpect(jsonPath("\$[1].showId", Matchers.greaterThan(offset.toInteger())))
                .andExpect(jsonPath("\$[0].categoryName").value(category.getCategoryName()))
                .andExpect(jsonPath("\$[1].categoryName").value(category.getCategoryName()))
                .andDo(MockMvcResultHandlers.print())
    }

    def "공연 아이디로 공연 상세 조회"() {

        given:
        showFacadeService.findShowById(1L) >> ShowFixtures.createShowDetailResponse(
                1L,
                1L,
                List.of(1L, 2L),
                List.of(1L, 2L, 3L),
                List.of(1L, 2L));

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shows/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").exists())
                .andExpect(jsonPath("\$.show.showId").value(1L))
                .andExpect(jsonPath("\$.show.place.placeId").value(1L))
                .andExpect(jsonPath("\$.rounds", Matchers.hasSize(2)))
                .andExpect(jsonPath("\$.rounds[0].performers", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$.rounds[1].performers", Matchers.hasSize(3)))
                .andExpect(jsonPath("\$.showSeatGrades", Matchers.hasSize(2)))
                .andDo(MockMvcResultHandlers.print())
    }
}
