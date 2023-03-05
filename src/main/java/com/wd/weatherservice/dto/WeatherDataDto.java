package com.wd.weatherservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherDataDto(
        @JsonProperty("max_temp") Integer maxTemp,
        @JsonProperty("min_temp") Integer minTemp,
        @JsonProperty("wind_spd") Integer windSpd,
        String datetime) {
}
