package com.wd.weatherservice.service;

import com.wd.weatherservice.Samples;
import com.wd.weatherservice.client.WeatherHttpClient;
import com.wd.weatherservice.compatator.WeatherComparator;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.exception.exception.FailedToFindForecastException;
import com.wd.weatherservice.model.Forecast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest implements Samples {

    @Mock
    private WeatherHttpClient weatherHttpClient;
    @Spy
    private Comparator<Forecast> comparator = new WeatherComparator();

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void should_correctly_return_the_best_location_for_windsurfing() {
        List<SixteenDayForecastDto> list = this.getListOfFiveForecastsForLocationsWithOneAsTheBestOne();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = weatherService.getTheBestLocationForWindsurfing("2023-02-28");

        assertThat(theBestLocationForWindsurfing).extracting("city", "country", "avgTemp", "windSpeed", "date")
                .containsExactlyElementsOf(List.of(tuple("Bridgetown", "BB", 25, 8, "2023-02-28")));
    }

    @Test
    void should_correctly_return_two_best_locations_for_windsurfing() {
        List<SixteenDayForecastDto> list = getListOfFiveForecastsForLocationsWithTwoAsTheBestOnes();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = weatherService.getTheBestLocationForWindsurfing("2023-02-28");

        assertThat(theBestLocationForWindsurfing).extracting("city", "country", "avgTemp", "windSpeed", "date")
                .containsExactlyInAnyOrderElementsOf(List.of(
                        tuple("Bridgetown", "BB", 25, 8, "2023-02-28"),
                        tuple("Jastarnia", "PL", 25, 8, "2023-02-28")));
    }

    @Test
    void should_throw_exception_when_trying_to_get_forecast_but_http_client_didnt_return_forecast_for_given_date() {
        List<SixteenDayForecastDto> list = getListOfForecastsWithMissingForecastForOneLocation();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        assertThatThrownBy(() -> weatherService.getTheBestLocationForWindsurfing("2023-02-28"))
                .isInstanceOf(FailedToFindForecastException.class)
                .hasMessage("Failed to find forecast for Wailea on 2023-02-28!");
    }

    @Test
    void should_return_empty_collection_when_any_of_location_doesnt_fulfil_criteria() {
        List<SixteenDayForecastDto> list = getNotFulfillingCriteriaForecasts();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = weatherService.getTheBestLocationForWindsurfing("2023-02-27");

        assertThat(theBestLocationForWindsurfing).isEmpty();
    }

    @Test
    void should_return_empty_collection_when_any_of_location_doesnt_fulfil_wind_speed_criteria() {
        List<SixteenDayForecastDto> list = getNotFulfillingWindSpeedCriteriaForecasts();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = weatherService.getTheBestLocationForWindsurfing("2023-02-27");

        assertThat(theBestLocationForWindsurfing).isEmpty();
    }

    @Test
    void should_return_empty_collection_when_any_of_location_doesnt_fulfil_average_temperature_criteria() {
        List<SixteenDayForecastDto> list = getNotFulfillingAverageTemperatureForecasts();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = weatherService.getTheBestLocationForWindsurfing("2023-02-27");

        assertThat(theBestLocationForWindsurfing).isEmpty();
    }

    @Test
    void should_correctly_count_avg_temperature() {
        SixteenDayForecastDto sampleForecast = getSampleForecast();
        when(weatherHttpClient.getWeatherForCity(anyString(), anyString())).thenReturn(sampleForecast);

        List<Forecast> theBestLocationForWindsurfing = weatherService.getTheBestLocationForWindsurfing("2023-03-01");

        assertThat(theBestLocationForWindsurfing.get(0).avgTemp()).isEqualTo(15);
    }


}