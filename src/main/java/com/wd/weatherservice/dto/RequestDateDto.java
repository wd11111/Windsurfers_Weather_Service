package com.wd.weatherservice.dto;

import com.wd.weatherservice.validation.MaxDate;

import java.time.LocalDate;

public record RequestDateDto(
        @MaxDate
        LocalDate localDate
) {
}
