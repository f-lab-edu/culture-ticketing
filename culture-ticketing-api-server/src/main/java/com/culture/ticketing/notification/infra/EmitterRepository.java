package com.culture.ticketing.notification.infra;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Repository
public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String emitterId, Object event);

    void deleteById(String emitterId);

    Map<String, Object> findAllEventCacheStartWithById(String userId);

    Map<String, SseEmitter> findAllEmitterStartWithById(String userId);
}
