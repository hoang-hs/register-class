
package com.example.registerclass.present.http.controller;

import com.example.registerclass.exception.AppException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class ExceptionController {

    static class ErrResource {

        @JsonProperty("http_code")
        private int httpCode;

        @JsonProperty("message")
        private String message;

        public ErrResource(int httpCode, String message) {
            this.httpCode = httpCode;
            this.message = message;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public void setHttpCode(int httpCode) {
            this.httpCode = httpCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrResource> handleException(AppException ex) {
        ErrResource err = new ErrResource(ex.getHttpStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(err);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrResource handlerValidationException(ValidationException ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.warn("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
//        ex.printStackTrace();
        return new ErrResource(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrResource handlerIllegalArgumentException(ValidationException ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.warn("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
//        ex.printStackTrace();
        return new ErrResource(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrResource handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.warn("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
//        ex.printStackTrace();
        logger.info(ex.getMessage());
        return new ErrResource(HttpStatus.BAD_REQUEST.value(), "invalid request");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrResource handleConstraintViolationException(MethodArgumentNotValidException ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.warn("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
//        ex.printStackTrace();
        return new ErrResource(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrResource handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.warn("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
//        ex.printStackTrace();
        return new ErrResource(HttpStatus.NOT_FOUND.value(), "method not support");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrResource handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.warn("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
//        ex.printStackTrace();
        return new ErrResource(HttpStatus.BAD_REQUEST.value(), "message not readable");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrResource globalExceptionHandler(Exception ex, WebRequest request) {
        String uuid = MDC.get("request_id");
        logger.error("id: {}, msg: {}, request description: {}", uuid, ex.getMessage(), request.getDescription(false));
        ex.printStackTrace();
        return new ErrResource(HttpStatus.INTERNAL_SERVER_ERROR.value(), "system error");
    }

}
