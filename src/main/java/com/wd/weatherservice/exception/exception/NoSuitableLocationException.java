package com.wd.weatherservice.exception.exception;

public class NoSuitableLocationException extends RuntimeException{

    public NoSuitableLocationException() {
        super("There is no suitable location for windsurfing");
    }
}
