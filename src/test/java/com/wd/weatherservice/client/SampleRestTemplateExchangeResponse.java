package com.wd.weatherservice.client;

import com.wd.weatherservice.dto.SixteenDayForecastDto;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface SampleRestTemplateExchangeResponse {

    default ResponseEntity<SixteenDayForecastDto> exchange(RestTemplate restTemplate) {
        return restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SixteenDayForecastDto>>any()
        );
    }
}
