package com.wd.weatherservice.client;

import com.wd.weatherservice.Samples;
import com.wd.weatherservice.dto.SixteenDayForecastDto;
import com.wd.weatherservice.exception.exception.ForecastNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForecastHttpClientImplTest implements SampleRestTemplateExchangeResponse, Samples {

    private RestTemplate restTemplate = mock(RestTemplate.class);

    ForecastHttpClientImpl httpClient = new ForecastHttpClientImpl(restTemplate, "/path", "key");

    @Test
    void should_correctly_return_sixteen_day_forecast() {
        when(exchange(restTemplate)).thenReturn(getResponseWithSampleForecast());

        SixteenDayForecastDto sixteenDayForecast = httpClient.getForecastForLocation("city", "country");

        assertThat(sixteenDayForecast).isEqualToComparingFieldByField(getSampleForecast());
    }

    @Test
    void should_throw_exception_when_response_body_is_null() {
        when(exchange(restTemplate)).thenReturn(getResponseWithNullBody());

        assertThatThrownBy(() -> httpClient.getForecastForLocation( "city", "country"))
                .isInstanceOf(ForecastNotFoundException.class)
                .hasMessage("Could not find forecast for city");
    }

    @Test
    void should_throw_wrapped_exception_when_exception_is_thrown_by_rest_template() {
        when(exchange(restTemplate)).thenThrow(RestClientException.class);

        assertThatThrownBy(() -> httpClient.getForecastForLocation("city", "country"))
                .isInstanceOf(ForecastNotFoundException.class)
                .hasMessage("Could not find forecast for city");
    }

}