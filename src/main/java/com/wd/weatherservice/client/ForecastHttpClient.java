package com.wd.weatherservice.client;

import com.wd.weatherservice.dto.SixteenDayForecastDto;

public interface ForecastHttpClient {

    SixteenDayForecastDto getForecastForLocation(String city, String country);
}
