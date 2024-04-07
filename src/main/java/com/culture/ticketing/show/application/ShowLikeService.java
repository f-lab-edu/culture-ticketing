package com.culture.ticketing.show.application;

import com.culture.ticketing.user.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ShowLikeService {

    private final RedisTemplate<Long, Long> redisTemplate;

    public ShowLikeService(RedisTemplate<Long, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public int createShowLike(User user, Long showId) {

        SetOperations<Long, Long> setOperations = redisTemplate.opsForSet();
        setOperations.add(showId, user.getUserId());

        return countShowLikesByShowId(showId);
    }

    @Transactional
    public int deleteShowLike(User user, Long showId) {

        SetOperations<Long, Long> setOperations = redisTemplate.opsForSet();
        setOperations.remove(showId, user.getUserId());

        return countShowLikesByShowId(showId);
    }

    @Transactional(readOnly = true)
    public int countShowLikesByShowId(Long showId) {

        Long count = redisTemplate.opsForSet().size(showId);

        return count == null ? 0 : count.intValue();
    }

    @Transactional(readOnly = true)
    public Boolean isShowLikeUser(User user, Long showId) {

        Set<Long> userIds = redisTemplate.opsForSet().members(showId);

        return user != null && userIds != null && userIds.contains(user.getUserId());
    }
}
