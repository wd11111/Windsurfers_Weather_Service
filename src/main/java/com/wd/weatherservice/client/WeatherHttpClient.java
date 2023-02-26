package com.wd.weatherservice.client;

import com.wd.weatherservice.model.WeatherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class WeatherHttpClient {

    private final RestTemplate restTemplate;
    private final String path;
    private final String key;

    public List<WeatherDto> getWeatherForCity(String city, String country) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<List<WeatherDto>> response = restTemplate.exchange(path, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<List<WeatherDto>>() {
                    }, getParams(city, country));

            final List<WeatherDto> body = response.getBody();
            return (body != null) ? body : Collections.emptyList();
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private Map<String, String> getParams(String city, String country) {
        return Map.of(
                "key", key,
                "city", city,
                "country", country);
    }
}
