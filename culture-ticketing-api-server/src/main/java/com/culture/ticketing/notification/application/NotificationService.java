package com.culture.ticketing.notification.application;

import com.culture.ticketing.notification.domain.Notification;
import com.culture.ticketing.notification.infra.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(Long userId, String message) {

        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .build();

        notificationRepository.save(notification);
    }
}
