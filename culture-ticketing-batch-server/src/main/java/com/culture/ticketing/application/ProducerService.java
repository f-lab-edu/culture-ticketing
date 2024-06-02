package com.culture.ticketing.application;

import com.culture.ticketing.notification.dto.BookingStartNotification;
import com.culture.ticketing.show.domain.Show;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class ProducerService {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final KafkaTemplate<String, BookingStartNotification> kafkaTemplate;
    private final ShowService showService;

    public ProducerService(RedisTemplate<Long, Long> redisTemplate, KafkaTemplate<String, BookingStartNotification> kafkaTemplate, ShowService showService) {
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.showService = showService;
    }

    public void createShowBookingStartNotifications() throws ExecutionException, InterruptedException {

        List<Show> shows = showService.findByBookingStartDateTimeLeftAnHour(LocalDateTime.now());

        SetOperations<Long, Long> setOperations = redisTemplate.opsForSet();

        for (Show show : shows) {
            Set<Long> userIdsByShowId = setOperations.members(show.getShowId());
            if (userIdsByShowId != null) {
                for (Long userId : userIdsByShowId) {
                    kafkaTemplate.send("booking-start-notifications",
                            BookingStartNotification.builder()
                                    .userId(userId)
                                    .showId(show.getShowId())
                                    .build())
                            .get();
                }
            }
        }
    }
}
