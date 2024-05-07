package com.culture.ticketing.application;

import com.culture.ticketing.user.domain.User;
import com.culture.ticketing.user.infra.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findByUserIds(Collection<Long> userIds) {

        return userRepository.findAllById(userIds);
    }
}
