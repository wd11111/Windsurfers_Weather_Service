package com.wd.weatherservice.service;

import com.wd.weatherservice.client.WeatherHttpClient;
import com.wd.weatherservice.compatator.WeatherComparator;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.exception.exception.NoSuitableLocationException;
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
        List<SixteenDayForecastDto> sixteenDayForecasts = Arrays.stream(Locations.values())
                .map(place -> weatherHttpClient.getWeatherForCity(place.getCity(), place.getCountry()))
                .toList();

        List<Forecast> forecasts = sixteenDayForecasts.stream()
                .map(sixteenDayForecast -> {
                    WeatherDataDto forecastForGivenDay = sixteenDayForecast.data()
                            .stream()
                            .filter(data -> data.datetime().equals(date))
                            .toList()
                            .get(0);

                    return new Forecast(
                            sixteenDayForecast.city_name(),
                            sixteenDayForecast.country_code(),
                            (forecastForGivenDay.min_temp() + forecastForGivenDay.max_temp()) / 2,
                            forecastForGivenDay.wind_spd()
                    );
                })
                .filter(forecast -> (forecast.windSpeed() >= MIN_WIND_SPEED && forecast.windSpeed() <= MAX_WIND_SPEED)
                        && (forecast.avgTemp() >= MIN_TEMP && forecast.avgTemp() <= MAX_TEMP))
                .sorted(comparator)
                .toList();

        int highestCalculatedValue = comparator.calculateValue(forecasts.get(0));

        return forecasts.stream()
                .filter(forecast -> comparator.calculateValue(forecast) == highestCalculatedValue)
                .toList();
    }

}
