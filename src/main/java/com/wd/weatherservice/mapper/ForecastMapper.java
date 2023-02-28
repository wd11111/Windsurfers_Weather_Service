package com.wd.weatherservice.mapper;

import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.exception.exception.FailedToFindForecastException;
import com.wd.weatherservice.model.Forecast;
import org.springframework.stereotype.Component;

@Component
public class ForecastMapper {

    public Forecast mapToForecast(String date, SixteenDayForecastDto sixteenDayForecast) {
        WeatherDataDto forecastForGivenDay = getForecastForGivenDay(sixteenDayForecast, date);

        return new Forecast(
                sixteenDayForecast.city_name(),
                sixteenDayForecast.country_code(),
                countAverageTemperature(forecastForGivenDay.min_temp(), forecastForGivenDay.max_temp()),
                forecastForGivenDay.wind_spd(),
                forecastForGivenDay.datetime()
        );
    }

    private WeatherDataDto getForecastForGivenDay(SixteenDayForecastDto forecasts, String date) {
        return forecasts.data()
                .stream()
                .filter(forecast -> forecast.datetime().equals(date))
                .toList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new FailedToFindForecastException(forecasts.city_name(), date));
    }

    private int countAverageTemperature(int minTemperature, int maxTemperature) {
        return (minTemperature + maxTemperature) / 2;
    }
}
