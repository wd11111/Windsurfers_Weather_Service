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
        int averageTemperature = countAverageTemperature(forecastForGivenDay.minTemp(), forecastForGivenDay.maxTemp());

        return Forecast.builder()
                .city(sixteenDayForecast.cityName())
                .country(sixteenDayForecast.countryCode())
                .avgTemp(averageTemperature)
                .windSpeed(forecastForGivenDay.windSpd())
                .date(forecastForGivenDay.datetime())
                .build();
    }

    private WeatherDataDto getForecastForGivenDay(SixteenDayForecastDto forecasts, String date) {
        return forecasts.data()
                .stream()
                .filter(forecast -> forecast.datetime().equals(date))
                .findFirst()
                .orElseThrow(() -> new FailedToFindForecastException(forecasts.cityName(), date));
    }

    private int countAverageTemperature(int minTemperature, int maxTemperature) {
        return (minTemperature + maxTemperature) / 2;
    }
}
