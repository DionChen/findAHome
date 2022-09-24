package com.example.findahome.models.exception;

import lombok.Data;

@Data
public class ApiNotFoundException extends RuntimeException {

    public ApiNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

