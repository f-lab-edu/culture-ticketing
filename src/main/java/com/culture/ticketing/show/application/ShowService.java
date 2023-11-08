package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.domain.AgeRestriction;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Transactional
    public void createShow(ShowSaveRequest request) {

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
                .build();

        showRepository.save(show);
    }

}
