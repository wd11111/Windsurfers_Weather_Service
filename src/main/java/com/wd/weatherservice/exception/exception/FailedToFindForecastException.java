package com.wd.weatherservice.exception.exception;

public class FailedToFindForecastException extends RuntimeException {

    public FailedToFindForecastException(String city, String date) {
        super(String.format("Failed to find forecast for %s on %s!", city, date));
    }
}
