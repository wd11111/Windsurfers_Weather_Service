package com.wd.weatherservice.dto;

import java.util.List;

public record SixteenDayForecastDto(
        String city_name,
        String country_code,
        List<WeatherDataDto> data) {
}
