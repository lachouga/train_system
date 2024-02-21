package com.example.TrainDemo.exception;

import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

public class TrainSystemException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    public TrainSystemException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
