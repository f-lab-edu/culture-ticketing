package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.PlaceMapById;
import com.culture.ticketing.show.exception.PlaceNotFoundException;
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
import java.util.Objects;
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

        checkValidShowSaveRequest(request);

        if (placeService.notExistsById(request.getPlaceId())) {
            throw new PlaceNotFoundException(request.getPlaceId());
        }

        showRepository.save(request.toEntity());
    }

    private void checkValidShowSaveRequest(ShowSaveRequest request) {

        Objects.requireNonNull(request.getCategory(), "공연 카테고리를 입력해주세요.");
        Objects.requireNonNull(request.getAgeRestriction(), "공연 관람 제한가를 입력해주세요.");
        Objects.requireNonNull(request.getPlaceId(), "공연 장소 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getShowStartDate(), "공연 시작 날짜를 입력해주세요.");
        Objects.requireNonNull(request.getShowEndDate(), "공연 종료 날짜를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowName()), "공연 이름을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getPosterImgUrl()), "공연 포스터 이미지 url을 입력해주세요.");
        Preconditions.checkArgument(request.getRunningTime() > 0, "공연 러닝 시간을 0 초과로 입력해주세요.");
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

        PlaceMapById placeMapById = placeService.findPlaceMapById(placeIds);

        return shows.stream()
                .map(show -> ShowResponse.from(show, placeMapById.getById(show.getPlaceId())))
                .collect(Collectors.toList());
    }
}
