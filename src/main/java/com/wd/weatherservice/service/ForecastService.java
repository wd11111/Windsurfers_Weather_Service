package com.wd.weatherservice.service;

import com.wd.weatherservice.client.ForecastHttpClient;
import com.wd.weatherservice.compatator.ForecastComparator;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.mapper.ForecastMapper;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.model.Locations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastService {

    public static final int MIN_WIND_SPEED = 5;
    public static final int MAX_WIND_SPEED = 18;
    public static final int MIN_TEMPERATURE = 5;
    public static final int MAX_TEMPERATURE = 35;

    private final ForecastHttpClient forecastHttpClient;
    private final ForecastMapper forecastMapper;
    private final Comparator<Forecast> comparator;

    public List<Forecast> getTheBestLocationForWindsurfing(String date) {
        List<SixteenDayForecastDto> sixteenDayForecasts = getSixteenDayForecastsFromHttpClient();

        List<Forecast> forecasts = sixteenDayForecasts.stream()
                .map(sixteenDayForecast -> forecastMapper.mapToForecast(date, sixteenDayForecast))
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
                .map(location -> forecastHttpClient.getForecastForLocation(location.getKey(), location.getValue()))
                .toList();
    }

    private List<Forecast> filterForecastsWithHighestValue(List<Forecast> forecasts) {
        int highestCalculatedValue = ForecastComparator.calculateValue(forecasts.get(0));

        return forecasts.stream()
                .filter(forecast -> ForecastComparator.calculateValue(forecast) == highestCalculatedValue)
                .toList();
    }

    private boolean doesLocationFulfilCriteria(Forecast forecast) {
        return (forecast.windSpeed() >= MIN_WIND_SPEED && forecast.windSpeed() <= MAX_WIND_SPEED)
                && (forecast.avgTemp() >= MIN_TEMPERATURE && forecast.avgTemp() <= MAX_TEMPERATURE);
    }

}
