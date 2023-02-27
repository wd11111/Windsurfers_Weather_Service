package com.wd.weatherservice.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Locations {

    private static final Map<String, String> locations;

    static {
        locations = new HashMap<>();

        locations.put("Jastarnia", "Poland");
        locations.put("Bridgetown", "Barbados");
        locations.put("Fortaleza", "Brazil");
        locations.put("Wailea", "Hawaii");
        locations.put("Pissouri", "Cyprus");
    }

    public static Map<String, String> getLocations() {
        return Collections.unmodifiableMap(locations);
    }
}
