package com.wd.weatherservice.service.stub;

import com.wd.weatherservice.Samples;
import com.wd.weatherservice.client.ForecastHttpClient;
import com.wd.weatherservice.dto.SixteenDayForecastDto;

public class ForecastHttpClientImplStub implements ForecastHttpClient, Samples {

    @Override
    public SixteenDayForecastDto getForecastForLocation(String city, String country) {
        return getSixteenDayForecast();
    }
}
