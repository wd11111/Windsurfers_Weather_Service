package com.wd.weatherservice.client;

import com.wd.weatherservice.dto.SixteenDayForecastDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class WeatherHttpClient {

    private final RestTemplate restTemplate;
    private final String path;
    private final String key;

    public SixteenDayForecastDto getWeatherForCity(String city, String country) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<SixteenDayForecastDto> response = restTemplate.exchange(
                    buildPath(city, country),
                    HttpMethod.GET, entity,
                    SixteenDayForecastDto.class
            );

            final SixteenDayForecastDto body = response.getBody();
            return (body != null) ? body : null;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private String buildPath(String city, String country) {
        return String.format("%s?key=%s&city=%s&country=%s", path, key, city, country);
    }
}
