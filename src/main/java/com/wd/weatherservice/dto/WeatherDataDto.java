package com.wd.weatherservice.dto;

public record WeatherDataDto(
        Integer max_temp,
        Integer min_temp,
        Integer wind_spd,
        String datetime) {
}
