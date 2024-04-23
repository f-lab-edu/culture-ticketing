package com.culture.ticketing.notification.application.dto;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookingStartNotification {

    private final User user;
    private final Show show;

    @Builder
    public BookingStartNotification(User user, Show show) {
        this.user = user;
        this.show = show;
    }

    public String getMessage() {

        return String.format("'%s' 공연 예매가 곧 시작됩니다.", show.getShowName());
    }
}
