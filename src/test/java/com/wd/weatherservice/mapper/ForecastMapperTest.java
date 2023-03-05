package com.wd.weatherservice.mapper;

import com.wd.weatherservice.Samples;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.exception.exception.FailedToFindForecastException;
import com.wd.weatherservice.model.Forecast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ForecastMapperTest implements Samples {

    ForecastMapper forecastMapper = new ForecastMapper();

    @Test
    void should_correctly_map_sixteen_day_forecast_to_one_day_forecast() {
        String date = "2023-02-27";
        SixteenDayForecastDto sixteenDayForecast = getSixteenDayForecast();

        Forecast result = forecastMapper.mapToForecast(date, sixteenDayForecast);

        assertThat(result).extracting(Forecast::city, Forecast::country, Forecast::windSpeed, Forecast::date)
                .isEqualTo(List.of(
                        sixteenDayForecast.cityName(),
                        sixteenDayForecast.countryCode(),
                        sixteenDayForecast.data().get(0).windSpd(),
                        date
                ));
    }

    @Test
    void should_throw_exception_when_failed_to_find_forecast_for_given_date() {
        String incorrectDate = "2020-01-01";
        SixteenDayForecastDto sixteenDayForecast = getSixteenDayForecast();

        assertThatThrownBy(() -> forecastMapper.mapToForecast(incorrectDate, sixteenDayForecast))
                .isInstanceOf(FailedToFindForecastException.class)
                .hasMessage("Failed to find forecast for Jastarnia on 2020-01-01!");
    }

    @Test
    void should_correctly_count_average_temperature() {
        String date = "2023-02-27";
        SixteenDayForecastDto sixteenDayForecast = getSixteenDayForecast();

        Forecast result = forecastMapper.mapToForecast(date, sixteenDayForecast);

        assertThat(result.avgTemp()).isEqualTo(4);
    }
}