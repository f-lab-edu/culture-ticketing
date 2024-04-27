package com.culture.ticketing.notification.application;

import com.culture.ticketing.notification.dto.BookingStartNotification;
import com.culture.ticketing.notification.infra.EmitterRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
public class EmitterService {

    private final EmitterRepository emitterRepository;

    public EmitterService(EmitterRepository emitterRepository) {
        this.emitterRepository = emitterRepository;
    }

    @KafkaListener(topics = "booking-start-notifications", groupId = "group_1")
    public void listen(BookingStartNotification notification) {

        String userId = String.valueOf(notification.getUserId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithById(userId);

        sseEmitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, notification.getMessage());
            sendToClient(emitter, key, notification.getMessage());
        });
    }

    public SseEmitter addEmitter(String userId, String lastEventId) {

        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(3600L));

        emitter.onCompletion(() -> {
            emitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            emitterRepository.deleteById(emitterId);
        });

        if (StringUtils.hasText(lastEventId)) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithById(userId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

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
