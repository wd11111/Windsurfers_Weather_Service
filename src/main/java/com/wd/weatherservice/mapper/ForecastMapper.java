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
        int averageTemperature = countAverageTemperature(forecastForGivenDay.min_temp(), forecastForGivenDay.max_temp());

        return Forecast.builder()
                .city(sixteenDayForecast.city_name())
                .country(sixteenDayForecast.country_code())
                .avgTemp(averageTemperature)
                .windSpeed(forecastForGivenDay.wind_spd())
                .date(forecastForGivenDay.datetime())
                .build();
    }

    private WeatherDataDto getForecastForGivenDay(SixteenDayForecastDto forecasts, String date) {
        return forecasts.data()
                .stream()
                .filter(forecast -> forecast.datetime().equals(date))
                .findFirst()
                .orElseThrow(() -> new FailedToFindForecastException(forecasts.city_name(), date));
    }

    private int countAverageTemperature(int minTemperature, int maxTemperature) {
        return (minTemperature + maxTemperature) / 2;
    }
}
