package com.wd.weatherservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Locations {

    JASTARNIA("Jastarnia", "Poland"),
    BRIDGETOWN("Bridgetown", "Barbados"),
    FORTALEZA("Fortaleza", "Brazil"),
    PISSOURI("Pissouri", "Cyprus");

    private final String city;
    private final String country;
}
