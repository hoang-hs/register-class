package com.example.registerclass.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException {

    public static AppException Default() {
        return new AppException("unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public static AppException WithMessage(String msg) {
        return new AppException(msg, HttpStatus.UNAUTHORIZED);
    }
}
