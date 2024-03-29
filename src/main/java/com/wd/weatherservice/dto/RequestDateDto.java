package com.wd.weatherservice.dto;

import com.wd.weatherservice.validation.MaxDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record RequestDateDto(
        @MaxDate
        @NotNull
        @FutureOrPresent
        LocalDate date) {
}
