package com.wd.weatherservice.service;

import com.wd.weatherservice.client.WeatherHttpClient;
import com.wd.weatherservice.compatator.WeatherComparator;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.model.Locations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    public static final int MIN_WIND_SPEED = 3;//5
    public static final int MAX_WIND_SPEED = 18;
    public static final int MIN_TEMP = 5;
    public static final int MAX_TEMP = 35;

    private final WeatherHttpClient weatherHttpClient;
    private final WeatherComparator comparator;

    public List<Forecast> getTheBestLocationForWindsurfing(String date) {
        List<SixteenDayForecastDto> sixteenDayForecasts = getSixteenDayForecastsFromHttpClient();

        List<Forecast> forecasts = sixteenDayForecasts.stream()
                .map(sixteenDayForecast -> mapToForecast(date, sixteenDayForecast))
                .filter(this::doesLocationFulfilCriteria)
                .sorted(comparator)
                .toList();

        int highestCalculatedValue = comparator.calculateValue(forecasts.get(0));

        return filterForecastsWithHighestValue(forecasts, highestCalculatedValue);
    }

    private List<SixteenDayForecastDto> getSixteenDayForecastsFromHttpClient() {
        return Arrays.stream(Locations.values())
                .map(place -> weatherHttpClient.getWeatherForCity(place.getCity(), place.getCountry()))
                .toList();
    }

    private List<Forecast> filterForecastsWithHighestValue(List<Forecast> forecasts, int highestCalculatedValue) {
        return forecasts.stream()
                .filter(forecast -> comparator.calculateValue(forecast) == highestCalculatedValue)
                .toList();
    }

    private Forecast mapToForecast(String date, SixteenDayForecastDto sixteenDayForecast) {
        WeatherDataDto forecastForGivenDay = getForecastForGivenDay(sixteenDayForecast.data(), date);

        return new Forecast(
                sixteenDayForecast.city_name(),
                sixteenDayForecast.country_code(),
                countAverageTemperature(forecastForGivenDay.min_temp(), forecastForGivenDay.max_temp()),
                forecastForGivenDay.wind_spd(),
                date
        );
    }

    private WeatherDataDto getForecastForGivenDay(List<WeatherDataDto> weatherDataDtoList, String date) {
        return weatherDataDtoList.stream()
                .filter(data -> data.datetime().equals(date))
                .toList()
                .get(0);
    }

    private int countAverageTemperature(int minTemperature, int maxTemperature) {
        return (minTemperature + maxTemperature) / 2;
    }

    private boolean doesLocationFulfilCriteria(Forecast forecast) {
        return (forecast.windSpeed() >= MIN_WIND_SPEED && forecast.windSpeed() <= MAX_WIND_SPEED)
                && (forecast.avgTemp() >= MIN_TEMP && forecast.avgTemp() <= MAX_TEMP);
    }

}
