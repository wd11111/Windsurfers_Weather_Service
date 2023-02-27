package com.wd.weatherservice.service;

import com.wd.weatherservice.client.WeatherHttpClient;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.exception.exception.NoSuitableLocationException;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.model.PlacesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    public static final int MIN_WIND_SPEED = 5;
    public static final int MAX_WIND_SPEED = 18;
    public static final int MIN_TEMP = 5;
    public static final int MAX_TEMP = 35;

    private final WeatherHttpClient weatherHttpClient;

    public Forecast getTheBestLocationForWindsurfing(String date) {
        List<SixteenDayForecastDto> sixteenDayForecasts = Arrays.stream(PlacesEnum.values())
                .map(place -> weatherHttpClient.getWeatherForCity(place.getCity(), place.getCountry()))
                .toList();

        return sixteenDayForecasts.stream()
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
                .max((o1, o2) -> Integer.compare(calculateValue(o1), calculateValue(o2)))
                .orElseThrow(NoSuitableLocationException::new);
    }

    private int calculateValue(Forecast forecast) {
        return forecast.windSpeed() * 3 + forecast.avgTemp();
    }

}
