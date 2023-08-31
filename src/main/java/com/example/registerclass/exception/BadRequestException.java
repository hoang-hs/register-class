package com.example.registerclass.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException {
    public static AppException Default() {
        return new AppException("invalid request", HttpStatus.BAD_REQUEST);
    }

    public static AppException WithMessage(String msg) {
        return new AppException(msg, HttpStatus.BAD_REQUEST);
    }
}
