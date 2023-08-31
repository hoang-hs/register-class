package com.example.registerclass.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException {
    public static AppException Default() {
        return new AppException("resource not found", HttpStatus.NOT_FOUND);
    }

    public static AppException WithMessage(String msg) {
        return new AppException(msg, HttpStatus.NOT_FOUND);
    }
}
