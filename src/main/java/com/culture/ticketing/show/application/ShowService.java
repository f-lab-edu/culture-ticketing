package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Place place = placeService.getPlaceByPlaceId(request.getPlaceId());

        Show show = Show.builder()
                .category(Category.valueOf(request.getCategoryCd()))
                .showName(request.getShowName())
                .ageRestriction(AgeRestriction.valueOf(request.getAgeRestrictionCd()))
                .showStartDate(request.getShowStartDate())
                .showEndDate(request.getShowEndDate())
                .runningTime(request.getRunningTime())
                .notice(request.getNotice())
                .posterImgUrl(request.getPosterImgUrl())
                .description(request.getDescription())
                .place(place)
                .build();

        showRepository.save(show);
    }

}
