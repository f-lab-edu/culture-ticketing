package com.culture.ticketing.user.application;

import com.culture.ticketing.user.application.dto.UserLoginRequest;
import com.culture.ticketing.user.application.dto.UserSaveRequest;
import com.culture.ticketing.user.domain.User;
import com.culture.ticketing.user.exception.DuplicatedUserEmailException;
import com.culture.ticketing.user.exception.UserNotFoundException;
import com.culture.ticketing.user.infra.UserRepository;
import com.google.common.base.Preconditions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(UserSaveRequest request) {

        checkValidUserSaveRequest(request);

        checkDuplicatedUserEmailExists(request.getEmail());

        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    private void checkValidUserSaveRequest(UserSaveRequest request) {

        Objects.requireNonNull(request.getRole(), "권한을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getEmail()), "이메일을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getPassword()), "비밀번호를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getUserName()), "이름을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getPhoneNumber()), "연락처를 입력해주세요.");
    }

    private void checkDuplicatedUserEmailExists(String email) {
        userRepository.findByEmail(email).ifPresent((user) -> {
            throw new DuplicatedUserEmailException();
        });
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long userId) {
        return !userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public Long login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new UserNotFoundException(request.getEmail());
        });

        user.checkPassword(request.getPassword(), passwordEncoder);

        return user.getUserId();
    }

    @Transactional(readOnly = true)
    public User findByUserId(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException(userId);
        });
    }

    @Transactional(readOnly = true)
    public List<User> findByUserIds(Collection<Long> userIds) {

        return userRepository.findAllById(userIds);
    }
}
