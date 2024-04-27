package com.culture.ticketing.application;

import com.culture.ticketing.notification.dto.BookingStartNotification;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.user.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final RedisTemplate<Long, Long> redisTemplate;
    private final KafkaTemplate<String, BookingStartNotification> kafkaTemplate;
    private final ShowService showService;
    private final UserService userService;

    public ProducerService(RedisTemplate<Long, Long> redisTemplate, KafkaTemplate<String, BookingStartNotification> kafkaTemplate, ShowService showService, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.showService = showService;
        this.userService = userService;
    }

    public void createShowBookingStartNotifications() {

        List<Show> shows = showService.findByBookingStartDateTimeLeftAnHour(LocalDateTime.now());

        SetOperations<Long, Long> setOperations = redisTemplate.opsForSet();
        List<Long> userIds = shows.stream()
                .map(show -> setOperations.members(show.getShowId()))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Map<Long, User> userMapById = userService.findByUserIds(userIds).stream()
                .collect(Collectors.toMap(User::getUserId, Function.identity()));

        for (Show show : shows) {
            Set<Long> userIdsByShowId = setOperations.members(show.getShowId());
            if (userIdsByShowId != null) {
                for (Long userId : userIdsByShowId) {
                    kafkaTemplate.send("booking-start-notifications",
                            BookingStartNotification.builder()
                                    .userId(userId)
                                    .userName(userMapById.get(userId).getUserName())
                                    .showId(show.getShowId())
                                    .showName(show.getShowName())
                                    .build());
                }
            }
        }
    }
}
