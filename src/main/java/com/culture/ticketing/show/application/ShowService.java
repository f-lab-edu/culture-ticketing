package com.culture.ticketing.show.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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

        if (request.getCategory() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_CATEGORY);
        }
        if (request.getAgeRestriction() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_AGE_RESTRICTION);
        }
        if (!StringUtils.hasText(request.getShowName())) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_NAME);
        }
        if (request.getRunningTime() <= 0) {
            throw new BaseException(BaseResponseStatus.NOT_POSITIVE_SHOW_RUNNING_TIME);
        }
        if (!StringUtils.hasText(request.getPosterImgUrl())) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_POSTER_IMG_URL);
        }
        if (request.getPlaceId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_PLACE_ID);
        }

        placeService.getPlaceByPlaceId(request.getPlaceId());
        Show show = request.toEntity();
        showRepository.save(show);
    }

    @Transactional(readOnly = true)
    public Show getShowByShowId(Long showId) {
        return showRepository.findById(showId).orElseThrow(ShowNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<ShowResponse> getShows(Long offset, int size) {
        return showRepository.findByShowIdGreaterThanLimit(offset, size).stream()
                .map(show -> new ShowResponse(show))
                .collect(Collectors.toList());
    }
}
