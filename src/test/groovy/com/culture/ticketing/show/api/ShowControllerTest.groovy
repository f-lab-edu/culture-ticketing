package com.culture.ticketing.show.api

import com.culture.ticketing.show.application.ShowFacadeService
import com.culture.ticketing.show.application.ShowService
import com.culture.ticketing.show.application.dto.ShowSaveRequest
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.fasterxml.jackson.databind.ObjectMapper
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

    def "공연_생성_성공"() {

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

    def "공연_생성_시_카테고리가_null_인_경우_400_에러"() {

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

    def "공연_생성_시_관람가가_null_인_경우_400_에러"() {

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

    def "공연_생성_시_공연_이름이_null_인_경우_400_에러"() {

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

    def "공연_생성_시_공연_이름이_빈_값인_경우_400_에러"() {

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

    def "공연_생성_시_러닝_시간이_0이하_인_경우_400_에러"() {

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

    def "공연_생성_시_포스터_이미지_url_이_null_인_경우_400_에러"() {

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

    def "공연_생성_시_포스터_이미지_url_이_빈_값인_경우_400_에러"() {

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

    def "공연_생성_시_공연_시작_날짜가_null_인_경우_400_에러"() {

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

    def "공연_생성_시_공연_종료_날짜가_null_인_경우_400_에러"() {

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

    def "공연_생성_시_장소_아이디_값이_null_인_경우_400_에러"() {

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
}
