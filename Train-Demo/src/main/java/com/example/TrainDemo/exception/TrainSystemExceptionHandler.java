package com.example.TrainDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TrainSystemExceptionHandler {
    @ExceptionHandler(value = {TrainNotFoundException.class})
    public ResponseEntity<Object> handleTrainNotFoundException
            (TrainNotFoundException trainNotFoundException)
    {
        TrainSystemException trainSystemException = new TrainSystemException(
                trainNotFoundException.getMessage(),
                trainNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(trainSystemException, HttpStatus.NOT_FOUND);
    }
}
