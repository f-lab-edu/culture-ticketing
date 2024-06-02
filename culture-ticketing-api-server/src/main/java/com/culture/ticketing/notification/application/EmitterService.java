package com.culture.ticketing.notification.application;

import com.culture.ticketing.notification.dto.BookingStartNotification;
import com.culture.ticketing.notification.infra.EmitterRepository;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
public class EmitterService {

    private final NotificationService notificationService;
    private final ShowService showService;
    private final UserService userService;
    private final EmitterRepository emitterRepository;

    public EmitterService(EmitterRepository emitterRepository, ShowService showService, UserService userService, NotificationService notificationService) {
        this.emitterRepository = emitterRepository;
        this.showService = showService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "booking-start-notifications", groupId = "group_1")
    public void listen(BookingStartNotification notification) {

        ShowResponse show = showService.findShowById(notification.getShowId());
        User user = userService.findByUserId(notification.getUserId());

        notificationService.createNotification(
                notification.getUserId(),
                String.format("%s 님이 관심 있어하는 '%s' 공연 예매가 곧 시작됩니다.", user.getUserName(), show.getShowName()));
    }

    public SseEmitter addEmitter(String userId) {

        SseEmitter emitter = emitterRepository.save(userId, new SseEmitter(3600L));

        emitter.onCompletion(() -> {
            emitterRepository.deleteById(userId);
        });
        emitter.onTimeout(() -> {
            emitterRepository.deleteById(userId);
        });

        sendToClient(emitter, userId, "connected!"); // 503 에러방지 더미 데이터

        return emitter;
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {

        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }
}
