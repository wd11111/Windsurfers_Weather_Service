package com.wd.weatherservice;

import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.model.Forecast;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Samples {

    default List<Forecast> getListOfFiveForecastsWithOneAsTheBestOne() {
        return List.of(
                new Forecast("Jastarnia", "PL", 2, 4, "2023-02-28"),
                new Forecast("Bridgetown", "BB", 25, 8, "2023-02-28"),
                new Forecast("Fortaleza", "BR", 27, 5, "2023-02-28"),
                new Forecast("Pissouri", "CY", 18, 3, "2023-02-28"),
                new Forecast("Wailea", "US", 21, 6, "2023-02-28")
        );
    }

    default List<Forecast> getListOfFiveForecastsWithTwoAsTheBestOnes() {
        return List.of(
                new Forecast("Jastarnia", "PL", 25, 8, "2023-02-28"),
                new Forecast("Bridgetown", "BB", 25, 8, "2023-02-28"),
                new Forecast("Fortaleza", "BR", 27, 5, "2023-02-28"),
                new Forecast("Pissouri", "CY", 18, 3, "2023-02-28"),
                new Forecast("Wailea", "US", 21, 6, "2023-02-28")
        );
    }

    default List<Forecast> getNotFulfillingCriteriaForecasts() {
        return List.of(
                new Forecast("Jastarnia", "PL", 1, 1, "2023-02-27"),
                new Forecast("Bridgetown", "BB", 1, 1, "2023-02-27"),
                new Forecast("Fortaleza", "BR", 1, 1, "2023-02-27"),
                new Forecast("Pissouri", "CY", 1, 1, "2023-02-27"),
                new Forecast("Wailea", "US", 1, 1, "2023-02-27")
        );
    }

    default List<Forecast> getNotFulfillingWindSpeedCriteriaForecasts() {
        return List.of(
                new Forecast("Jastarnia", "PL", 20, 1, "2023-02-27"),
                new Forecast("Bridgetown", "BB", 20, 1, "2023-02-27"),
                new Forecast("Fortaleza", "BR", 20, 1, "2023-02-27"),
                new Forecast("Pissouri", "CY", 20, 1, "2023-02-27"),
                new Forecast("Wailea", "US", 20, 1, "2023-02-27")
        );
    }

    default List<Forecast> getNotFulfillingAverageTemperatureForecasts() {
        return List.of(
                new Forecast("Jastarnia", "PL", 1, 10, "2023-02-27"),
                new Forecast("Bridgetown", "BB", 1, 10, "2023-02-27"),
                new Forecast("Fortaleza", "BR", 1, 10, "2023-02-27"),
                new Forecast("Pissouri", "CY", 1, 10, "2023-02-27"),
                new Forecast("Wailea", "US", 1, 10, "2023-02-27")
        );
    }

    default SixteenDayForecastDto getSampleForecast() {
        return new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                new WeatherDataDto(10, 20, 5, "2023-03-01")
        ));
    }

    default Forecast getSampleForecastResponse() {
        return new Forecast("Jastarnia", "PL", 20, 10, "2023-03-01");
    }

    default ResponseEntity<SixteenDayForecastDto> getResponseWithSampleForecast() {
        return new ResponseEntity<>(getSampleForecast(), HttpStatus.ACCEPTED);
    }

    default ResponseEntity<SixteenDayForecastDto> getResponseWithNullBody() {
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    default SixteenDayForecastDto getSixteenDayForecast() {
        return new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                new WeatherDataDto(2, 6, 4, "2023-02-27"),
                new WeatherDataDto(4, 2, 4, "2023-02-28")
        ));
    }

}
