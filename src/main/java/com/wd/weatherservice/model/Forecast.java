package com.wd.weatherservice.model;

import lombok.Builder;

@Builder
public record Forecast(
        String city,
        String country,
        Integer avgTemp,
        Integer windSpeed,
        String date) {
}
