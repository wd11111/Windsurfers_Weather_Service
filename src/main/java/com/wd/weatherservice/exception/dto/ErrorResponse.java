package com.wd.weatherservice.exception.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus httpStatus) {
}
