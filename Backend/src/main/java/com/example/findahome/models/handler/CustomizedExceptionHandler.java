package com.example.findahome.models.handler;


import com.example.findahome.models.exception.ApiBadRequestException;
import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(value = {ApiNotFoundException.class, ApiBadRequestException.class})
    public ResponseEntity<ExceptionResponse> CustomizedExceptionHandler(RuntimeException e, WebRequest request) {
        ExceptionResponse message = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(Exception e, WebRequest request) {
        ExceptionResponse message = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
