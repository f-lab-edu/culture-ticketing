package com.culture.ticketing.application;

import com.culture.ticketing.notification.dto.BookingStartNotification;
import com.culture.ticketing.show.domain.Show;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProducerService {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final KafkaTemplate<String, BookingStartNotification> kafkaTemplate;

    public ProducerService(RedisTemplate<Long, Long> redisTemplate, KafkaTemplate<String, BookingStartNotification> kafkaTemplate) {
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishShowBookingStartNotifications(Show show) {

        SetOperations<Long, Long> setOperations = redisTemplate.opsForSet();

        Set<Long> userIdsByShowId = setOperations.members(show.getShowId());
        if (userIdsByShowId == null) {
            return;
        }

        for (Long userId : userIdsByShowId) {
            kafkaTemplate.send("booking-start-notifications",
                    BookingStartNotification.builder()
                            .userId(userId)
                            .showId(show.getShowId())
                            .build());
        }
    }
}
