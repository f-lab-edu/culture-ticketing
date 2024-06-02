package com.culture.ticketing.notification.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingStartNotification {

    private Long userId;
    private Long showId;

    @Builder
    public BookingStartNotification(Long userId, Long showId) {
        this.userId = userId;
        this.showId = showId;
    }
}
