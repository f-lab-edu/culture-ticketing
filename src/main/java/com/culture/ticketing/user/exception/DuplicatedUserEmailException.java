package com.culture.ticketing.user.exception;

public class DuplicatedUserEmailException extends RuntimeException {

    public DuplicatedUserEmailException() {
        super("이미 등록된 이메일입니다.");
    }
}