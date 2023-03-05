package com.wd.weatherservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SixteenDayForecastDto(
        @JsonProperty("city_name") String cityName,
        @JsonProperty("country_code") String countryCode,
        List<WeatherDataDto> data) {
}
