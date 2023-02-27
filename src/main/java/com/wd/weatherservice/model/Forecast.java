package com.wd.weatherservice.model;

public record Forecast(
        String city,
        String country,
        Integer avgTemp,
        Integer windSpeed
) {
}
