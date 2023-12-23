package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.place.exception.PlaceNotFoundException;
import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
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

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_CATEGORY;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_AGE_RESTRICTION;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_PLACE_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_START_DATE;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_END_DATE;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_POSTER_IMG_URL;
import static com.culture.ticketing.common.response.BaseResponseStatus.NOT_POSITIVE_SHOW_RUNNING_TIME;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final PlaceService placeService;
    private final RoundService roundService;

    public ShowService(ShowRepository showRepository, PlaceService placeService, RoundService roundService) {
        this.showRepository = showRepository;
        this.placeService = placeService;
        this.roundService = roundService;
    }

    @Transactional
    public void createShow(ShowSaveRequest request) {

        Objects.requireNonNull(request.getCategory(), EMPTY_SHOW_CATEGORY.getMessage());
        Objects.requireNonNull(request.getAgeRestriction(), EMPTY_SHOW_AGE_RESTRICTION.getMessage());
        Objects.requireNonNull(request.getPlaceId(), EMPTY_SHOW_PLACE_ID.getMessage());
        Objects.requireNonNull(request.getShowStartDate(), EMPTY_SHOW_START_DATE.getMessage());
        Objects.requireNonNull(request.getShowEndDate(), EMPTY_SHOW_END_DATE.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getShowName()), EMPTY_SHOW_NAME.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getPosterImgUrl()), EMPTY_SHOW_POSTER_IMG_URL.getMessage());
        Preconditions.checkArgument(request.getRunningTime() > 0, NOT_POSITIVE_SHOW_RUNNING_TIME.getMessage());

        if (!placeService.existsById(request.getPlaceId())) {
            throw new PlaceNotFoundException(request.getPlaceId());
        }

        Show show = request.toEntity();
        showRepository.save(show);
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowDetailResponseById(Long showId) {

        Show show = showRepository.findById(showId).orElseThrow(() -> {
            throw new ShowNotFoundException(showId);
        });
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<RoundResponse> rounds = roundService.findRoundsByShowId(show.getShowId());

        return ShowDetailResponse.from(show, place, rounds);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long showId) {
        return showRepository.existsById(showId);
    }

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

    private void checkPlaceExistInShows(List<Show> shows, Map<Long, Place> placeMapByPlaceId) {

        for (Show show : shows) {
            if (!placeMapByPlaceId.containsKey(show.getPlaceId())) {
                throw new PlaceNotFoundException(show.getPlaceId());
            }
        }
    }
}
