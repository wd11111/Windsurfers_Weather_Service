package com.wd.weatherservice;

import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.dto.WeatherDataDto;
import com.wd.weatherservice.model.Forecast;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Samples {

    default List<SixteenDayForecastDto> getListOfFiveForecastsForLocationsWithOneAsTheBestOne() {
        return List.of(
                new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                        new WeatherDataDto(2, 1, 4, "2023-02-27"),
                        new WeatherDataDto(4, 1, 4, "2023-02-28")
                )),
                new SixteenDayForecastDto("Bridgetown", "BB", List.of(
                        new WeatherDataDto(26, 23, 4, "2023-02-27"),
                        new WeatherDataDto(26, 24, 8, "2023-02-28")
                )),
                new SixteenDayForecastDto("Fortaleza", "BR", List.of(
                        new WeatherDataDto(27, 24, 4, "2023-02-27"),
                        new WeatherDataDto(29, 25, 5, "2023-02-28")
                )),
                new SixteenDayForecastDto("Pissouri", "CY", List.of(
                        new WeatherDataDto(21, 11, 2, "2023-02-27"),
                        new WeatherDataDto(23, 14, 3, "2023-02-28")
                )),
                new SixteenDayForecastDto("Wailea", "US", List.of(
                        new WeatherDataDto(24, 18, 4, "2023-02-27"),
                        new WeatherDataDto(23, 19, 6, "2023-02-28")
                ))
        );
    }

    default List<SixteenDayForecastDto> getListOfFiveForecastsForLocationsWithTwoAsTheBestOnes() {
        return List.of(
                new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                        new WeatherDataDto(2, 1, 4, "2023-02-27"),
                        new WeatherDataDto(26, 24, 8, "2023-02-28")
                )),
                new SixteenDayForecastDto("Bridgetown", "BB", List.of(
                        new WeatherDataDto(26, 23, 4, "2023-02-27"),
                        new WeatherDataDto(26, 24, 8, "2023-02-28")
                )),
                new SixteenDayForecastDto("Fortaleza", "BR", List.of(
                        new WeatherDataDto(27, 24, 4, "2023-02-27"),
                        new WeatherDataDto(29, 25, 5, "2023-02-28")
                )),
                new SixteenDayForecastDto("Pissouri", "CY", List.of(
                        new WeatherDataDto(21, 11, 2, "2023-02-27"),
                        new WeatherDataDto(23, 14, 3, "2023-02-28")
                )),
                new SixteenDayForecastDto("Wailea", "US", List.of(
                        new WeatherDataDto(24, 18, 4, "2023-02-27"),
                        new WeatherDataDto(23, 19, 6, "2023-02-28")
                ))
        );
    }

    default List<SixteenDayForecastDto> getListOfForecastsWithMissingForecastForOneLocation() {
        return List.of(
                new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                        new WeatherDataDto(2, 1, 4, "2023-02-27"),
                        new WeatherDataDto(4, 1, 4, "2023-02-28")
                )),
                new SixteenDayForecastDto("Bridgetown", "BB", List.of(
                        new WeatherDataDto(26, 23, 4, "2023-02-27"),
                        new WeatherDataDto(26, 24, 8, "2023-02-28")
                )),
                new SixteenDayForecastDto("Fortaleza", "BR", List.of(
                        new WeatherDataDto(27, 24, 4, "2023-02-27"),
                        new WeatherDataDto(29, 25, 5, "2023-02-28")
                )),
                new SixteenDayForecastDto("Pissouri", "CY", List.of(
                        new WeatherDataDto(21, 11, 2, "2023-02-27"),
                        new WeatherDataDto(23, 14, 3, "2023-02-28")
                )),
                new SixteenDayForecastDto("Wailea", "US", List.of(
                        new WeatherDataDto(24, 18, 4, "2023-02-27")
                ))
        );
    }

    default List<SixteenDayForecastDto> getNotFulfillingCriteriaForecasts() {
        return List.of(
                new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                        new WeatherDataDto(1, 1, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Bridgetown", "BB", List.of(
                        new WeatherDataDto(1, 1, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Fortaleza", "BR", List.of(
                        new WeatherDataDto(1, 1, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Pissouri", "CY", List.of(
                        new WeatherDataDto(1, 1, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Wailea", "US", List.of(
                        new WeatherDataDto(1, 1, 1, "2023-02-27")
                ))
        );
    }

    default List<SixteenDayForecastDto> getNotFulfillingWindSpeedCriteriaForecasts() {
        return List.of(
                new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                        new WeatherDataDto(20, 20, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Bridgetown", "BB", List.of(
                        new WeatherDataDto(20, 20, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Fortaleza", "BR", List.of(
                        new WeatherDataDto(20, 20, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Pissouri", "CY", List.of(
                        new WeatherDataDto(20, 20, 1, "2023-02-27")
                )),
                new SixteenDayForecastDto("Wailea", "US", List.of(
                        new WeatherDataDto(20, 20, 1, "2023-02-27")
                ))
        );
    }

    default List<SixteenDayForecastDto> getNotFulfillingAverageTemperatureForecasts() {
        return List.of(
                new SixteenDayForecastDto("Jastarnia", "PL", List.of(
                        new WeatherDataDto(1, 1, 10, "2023-02-27")
                )),
                new SixteenDayForecastDto("Bridgetown", "BB", List.of(
                        new WeatherDataDto(1, 1, 10, "2023-02-27")
                )),
                new SixteenDayForecastDto("Fortaleza", "BR", List.of(
                        new WeatherDataDto(1, 1, 10, "2023-02-27")
                )),
                new SixteenDayForecastDto("Pissouri", "CY", List.of(
                        new WeatherDataDto(1, 1, 10, "2023-02-27")
                )),
                new SixteenDayForecastDto("Wailea", "US", List.of(
                        new WeatherDataDto(1, 1, 10, "2023-02-27")
                ))
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
}
