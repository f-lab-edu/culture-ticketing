package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShowLikeService {

    private final ShowService showService;
    private final UserService userService;
    private final RedisTemplate<Long, Set<Long>> redisTemplate;

    public ShowLikeService(ShowService showService, UserService userService, RedisTemplate<Long, Set<Long>> redisTemplate) {
        this.showService = showService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public void createShowLike(User user, Long showId) {

        ShowResponse show = showService.findShowById(showId);

        ValueOperations<Long, Set<Long>> valueOperations = redisTemplate.opsForValue();
        Set<Long> userIds = valueOperations.get(show.getShowId());
        if (userIds == null) {
            userIds = new HashSet<>();
        }
        userIds.add(user.getUserId());
        valueOperations.set(show.getShowId(), userIds);
    }

    @Transactional
    public void deleteShowLike(User user, Long showId) {

        ValueOperations<Long, Set<Long>> valueOperations = redisTemplate.opsForValue();
        Set<Long> userIds = valueOperations.get(showId);
        if (userIds != null) {
            userIds.remove(user.getUserId());
            valueOperations.set(showId, userIds);
        }
    }

    @Transactional(readOnly = true)
    public int countShowLikesByShowId(Long showId) {

        Set<Long> userIds = redisTemplate.opsForValue().get(showId);

        return userIds == null ? 0 : userIds.size();
    }
}
