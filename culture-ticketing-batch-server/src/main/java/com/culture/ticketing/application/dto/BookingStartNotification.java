package com.culture.ticketing.application.dto;

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
}
