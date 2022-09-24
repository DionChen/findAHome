package com.example.findahome.models.exception;

import lombok.Data;

@Data
public class ExceptionResponse {

    private int statusCode;

    private String message;

    private String description;

    public ExceptionResponse(int statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }
}
