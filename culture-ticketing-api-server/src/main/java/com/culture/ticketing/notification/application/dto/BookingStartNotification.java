package com.culture.ticketing.notification.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookingStartNotification {

    private final Long userId;
    private final String userName;
    private final Long showId;
    private final String showName;

    @Builder
    public BookingStartNotification(Long userId, String userName, Long showId, String showName) {
        this.userId = userId;
        this.userName = userName;
        this.showId = showId;
        this.showName = showName;
    }

    public String getMessage() {

        return String.format("%s 님이 관심 있어하는 '%s' 공연 예매가 곧 시작됩니다.", userName, showName);
    }
}
