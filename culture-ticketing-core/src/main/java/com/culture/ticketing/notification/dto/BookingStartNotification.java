package com.culture.ticketing.notification.dto;

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

    public String getMessage() {

        return String.format("%s 님이 관심 있어하는 '%s' 공연 예매가 곧 시작됩니다.", userName, showName);
    }
}
