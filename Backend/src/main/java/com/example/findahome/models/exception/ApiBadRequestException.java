package com.example.findahome.models.exception;

import lombok.Data;

@Data
public class ApiBadRequestException extends RuntimeException {


    public ApiBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
