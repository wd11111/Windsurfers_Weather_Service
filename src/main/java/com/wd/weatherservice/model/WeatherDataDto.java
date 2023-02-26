package com.wd.weatherservice.model;

public record WeatherDataDto(
        Integer high_temp,
        Integer low_temp,
        Integer wind_spd) {
}
