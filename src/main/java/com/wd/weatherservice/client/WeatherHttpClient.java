package com.wd.weatherservice.client;

import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.exception.exception.ForecastNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RequiredArgsConstructor
public class WeatherHttpClient {

    private final RestTemplate restTemplate;
    private final String path;
    private final String key;

    public SixteenDayForecastDto getWeatherForCity(String city, String country) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<SixteenDayForecastDto> response = restTemplate.exchange(
                    buildPath(city, country),
                    HttpMethod.GET, entity,
                    SixteenDayForecastDto.class
            );
            final SixteenDayForecastDto body = response.getBody();

            return validateBody(city, body);
        } catch (RestClientException e) {
            log.error(e.getMessage());
            throw new ForecastNotFoundException(city);
        }
    }

    private String buildPath(String city, String country) {
        return String.format("%s?key=%s&city=%s&country=%s", path, key, city, country);
    }

    private SixteenDayForecastDto validateBody(String city, SixteenDayForecastDto body) {
        if (body == null) {
            throw new ForecastNotFoundException(city);
        }
        return body;
    }

}
