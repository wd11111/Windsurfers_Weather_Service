package com.wd.weatherservice.client;

import com.wd.weatherservice.exception.handler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class HttpClientConfig {

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    RestTemplate restTemplate(@Value("${http.client.config.connectionTimeout}") long connectionTimeout,
                              @Value("${http.client.config.readTimeout}") long readTimeout,
                              RestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    WeatherHttpClient offerClient(RestTemplate restTemplate,
                                  @Value("${http.client.config.key}") String key,
                                  @Value("${http.client.config.path}") String path) {
        return new WeatherHttpClient(restTemplate, path, key);
    }
}
