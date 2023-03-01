package com.wd.weatherservice.service;

import com.wd.weatherservice.Samples;
import com.wd.weatherservice.client.ForecastHttpClient;
import com.wd.weatherservice.compatator.ForecastComparator;
import com.wd.weatherservice.mapper.ForecastMapper;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.service.stub.ForecastHttpClientImplStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForecastServiceTest implements Samples {

    @Mock
    private ForecastMapper forecastMapper;
    @Spy
    private ForecastHttpClient forecastHttpClient = new ForecastHttpClientImplStub();
    @Spy
    private Comparator<Forecast> comparator = new ForecastComparator();

    @InjectMocks
    private ForecastService forecastService;

    @Test
    void should_correctly_return_the_best_location_for_windsurfing() {
        List<Forecast> list = getListOfFiveForecastsWithOneAsTheBestOne();
        when(forecastMapper.mapToForecast(anyString(), any())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = forecastService.getTheBestLocationForWindsurfing("2023-02-28");

        assertThat(theBestLocationForWindsurfing).extracting("city", "country", "avgTemp", "windSpeed", "date")
                .containsExactlyElementsOf(List.of(tuple("Bridgetown", "BB", 25, 8, "2023-02-28")));
    }

    @Test
    void should_correctly_return_two_best_locations_for_windsurfing() {
        List<Forecast> list = getListOfFiveForecastsWithTwoAsTheBestOnes();
        when(forecastMapper.mapToForecast(anyString(), any())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = forecastService.getTheBestLocationForWindsurfing("2023-02-28");

        assertThat(theBestLocationForWindsurfing).extracting("city", "country", "avgTemp", "windSpeed", "date")
                .containsExactlyInAnyOrderElementsOf(List.of(
                        tuple("Bridgetown", "BB", 25, 8, "2023-02-28"),
                        tuple("Jastarnia", "PL", 25, 8, "2023-02-28")));
    }

    @Test
    void should_return_empty_collection_when_any_of_locations_fulfil_criteria() {
        List<Forecast> list = getNotFulfillingCriteriaForecasts();
        when(forecastMapper.mapToForecast(anyString(), any())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = forecastService.getTheBestLocationForWindsurfing("2023-02-27");

        assertThat(theBestLocationForWindsurfing).isEmpty();
    }

    @Test
    void should_return_empty_collection_when_any_of_locations_fulfil_wind_speed_criteria() {
        List<Forecast> list = getNotFulfillingWindSpeedCriteriaForecasts();
        when(forecastMapper.mapToForecast(anyString(), any())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = forecastService.getTheBestLocationForWindsurfing("2023-02-27");

        assertThat(theBestLocationForWindsurfing).isEmpty();
    }

    @Test
    void should_return_empty_collection_when_any_of_locations_fulfil_average_temperature_criteria() {
        List<Forecast> list = getNotFulfillingAverageTemperatureForecasts();
        when(forecastMapper.mapToForecast(anyString(), any())).thenReturn(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));

        List<Forecast> theBestLocationForWindsurfing = forecastService.getTheBestLocationForWindsurfing("2023-02-27");

        assertThat(theBestLocationForWindsurfing).isEmpty();
    }

}