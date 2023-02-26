package com.wd.weatherservice.model;

import java.util.List;

public record WeatherDto(
        String city_name,
        List<WeatherDataDto> data
) {


}
