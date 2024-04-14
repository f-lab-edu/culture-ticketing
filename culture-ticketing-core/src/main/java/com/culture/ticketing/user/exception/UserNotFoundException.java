package com.culture.ticketing.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(String.format("유저가 존재하지 않습니다. (userId = %d)", userId));
    }

    public UserNotFoundException(String email) {
        super(String.format("유저가 존재하지 않습니다. (email = %s)", email));
    }
}
