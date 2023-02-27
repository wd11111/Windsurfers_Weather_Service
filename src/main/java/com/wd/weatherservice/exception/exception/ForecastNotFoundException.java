package com.wd.weatherservice.exception.exception;

public class ForecastNotFoundException extends RuntimeException {

    public ForecastNotFoundException(String city) {
        super(String.format("Could not find forecast for %s", city));
    }
}
