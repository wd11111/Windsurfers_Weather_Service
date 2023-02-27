package com.wd.weatherservice.compatator;

import com.wd.weatherservice.model.Forecast;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ForecastComparator implements Comparator<Forecast> {
    @Override
    public  int compare(Forecast o1, Forecast o2) {
        return Integer.compare(calculateValue(o2), calculateValue(o1));
    }

    public static int calculateValue(Forecast forecast) {
        return forecast.windSpeed() * 3 + forecast.avgTemp();
    }
}
