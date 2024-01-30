package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.place.exception.PlaceNotFoundException;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.ShowRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final PlaceService placeService;

    public ShowService(ShowRepository showRepository, PlaceService placeService) {
        this.showRepository = showRepository;
        this.placeService = placeService;
    }

    @Transactional
    public void createShow(ShowSaveRequest request) {

        Objects.requireNonNull(request.getCategory(), "공연 카테고리를 입력해주세요.");
        Objects.requireNonNull(request.getAgeRestriction(), "공연 관람 제한가를 입력해주세요.");
        Objects.requireNonNull(request.getPlaceId(), "공연 장소 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getShowStartDate(), "공연 시작 날짜를 입력해주세요.");
        Objects.requireNonNull(request.getShowEndDate(), "공연 종료 날짜를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowName()), "공연 이름을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getPosterImgUrl()), "공연 포스터 이미지 url을 입력해주세요.");
        Preconditions.checkArgument(request.getRunningTime() > 0, "공연 러닝 시간을 0 초과로 입력해주세요.");

        if (placeService.notExistsById(request.getPlaceId())) {
            throw new PlaceNotFoundException(request.getPlaceId());
        }

        showRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public Show findShowById(Long showId) {

        return showRepository.findById(showId).orElseThrow(() -> {
            throw new ShowNotFoundException(showId);
        });
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long showId) {
        return !showRepository.existsById(showId);
    }

    @Transactional(readOnly = true)
    public List<ShowResponse> findShows(Long offset, int size, Category category) {

        List<Show> shows = showRepository.findByShowIdGreaterThanLimitAndCategory(offset, size, category);

        List<Long> placeIds = shows.stream()
                .map(Show::getPlaceId)
                .collect(Collectors.toList());

        Map<Long, Place> placeMapByPlaceId = placeService.findPlacesByIds(placeIds).stream()
                .collect(Collectors.toMap(Place::getPlaceId, Function.identity()));

        checkPlaceExistInShows(shows, placeMapByPlaceId);

        return shows.stream()
                .map(show -> ShowResponse.from(show, placeMapByPlaceId.get(show.getPlaceId())))
                .collect(Collectors.toList());
    }

    protected void checkPlaceExistInShows(List<Show> shows, Map<Long, Place> placeMapByPlaceId) {

        for (Show show : shows) {
            if (!placeMapByPlaceId.containsKey(show.getPlaceId())) {
                throw new PlaceNotFoundException(show.getPlaceId());
            }
        }
    }
}
