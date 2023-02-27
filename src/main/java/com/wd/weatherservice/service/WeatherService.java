package com.wd.weatherservice.service;

import com.wd.weatherservice.client.WeatherHttpClient;
import com.wd.weatherservice.compatator.WeatherComparator;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.exception.exception.FailedToFindForecastException;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.model.Locations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    public static final int MIN_WIND_SPEED = 5;
    public static final int MAX_WIND_SPEED = 18;
    public static final int MIN_TEMP = 5;
    public static final int MAX_TEMP = 35;

    private final WeatherHttpClient weatherHttpClient;
    private final Comparator<Forecast> comparator;

    public List<Forecast> getTheBestLocationForWindsurfing(String date) {
        List<SixteenDayForecastDto> sixteenDayForecasts = getSixteenDayForecastsFromHttpClient();

        List<Forecast> forecasts = sixteenDayForecasts.stream()
                .map(sixteenDayForecast -> mapToForecast(date, sixteenDayForecast))
                .filter(this::doesLocationFulfilCriteria)
                .sorted(comparator)
                .toList();

        if (forecasts.isEmpty()) {
            return Collections.emptyList();
        }

        return filterForecastsWithHighestValue(forecasts);
    }

    private List<SixteenDayForecastDto> getSixteenDayForecastsFromHttpClient() {
        return Locations.getLocations().entrySet()
                .stream()
                .map(location -> weatherHttpClient.getWeatherForCity(location.getKey(), location.getValue()))
                .toList();
    }

    private List<Forecast> filterForecastsWithHighestValue(List<Forecast> forecasts) {
        int highestCalculatedValue = WeatherComparator.calculateValue(forecasts.get(0));

        return forecasts.stream()
                .filter(forecast -> WeatherComparator.calculateValue(forecast) == highestCalculatedValue)
                .toList();
    }

    private Forecast mapToForecast(String date, SixteenDayForecastDto sixteenDayForecast) {
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
                .findAny()
                .orElseThrow(() -> new FailedToFindForecastException(forecasts.city_name(), date));
    }

    private int countAverageTemperature(int minTemperature, int maxTemperature) {
        return (minTemperature + maxTemperature) / 2;
    }

    private boolean doesLocationFulfilCriteria(Forecast forecast) {
        return (forecast.windSpeed() >= MIN_WIND_SPEED && forecast.windSpeed() <= MAX_WIND_SPEED)
                && (forecast.avgTemp() >= MIN_TEMP && forecast.avgTemp() <= MAX_TEMP);
    }

}
