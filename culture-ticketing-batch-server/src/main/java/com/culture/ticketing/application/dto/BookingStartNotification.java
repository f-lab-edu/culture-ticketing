package com.culture.ticketing.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingStartNotification {

    private Long userId;
    private String userName;
    private Long showId;
    private String showName;

    @Builder
    public BookingStartNotification(Long userId, String userName, Long showId, String showName) {
        this.userId = userId;
        this.userName = userName;
        this.showId = showId;
        this.showName = showName;
    }
}
