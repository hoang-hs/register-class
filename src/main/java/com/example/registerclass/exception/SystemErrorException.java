package com.example.registerclass.exception;

import org.springframework.http.HttpStatus;

public class SystemErrorException {

    public static AppException Default() {
        return new AppException("system error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static AppException WithMessage(String msg) {
        return new AppException(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
