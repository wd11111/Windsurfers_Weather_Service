package com.wd.weatherservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wd.weatherservice.Samples;
import com.wd.weatherservice.client.ForecastHttpClientImpl;
import com.wd.weatherservice.compatator.ForecastComparator;
import com.wd.weatherservice.dto.RequestDateDto;
import com.wd.weatherservice.exception.handler.RestExceptionHandler;
import com.wd.weatherservice.mapper.ForecastMapper;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.service.ForecastService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Comparator;
import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest()
@ContextConfiguration(classes = MockMvcConfig.class)
class ForecastControllerTest implements Samples {

    @MockBean
    private ForecastService forecastService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_ok_status_when_get_for_best_locations() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new RequestDateDto(now().plusDays(5)));
        List<Forecast> response = List.of(getSampleForecastResponse());
        String expectedResponseBody = objectMapper.writeValueAsString(response);
        when(forecastService.getTheBestLocationForWindsurfing(anyString())).thenReturn(response);

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/api/forecasts")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    void should_return_bad_request_status_when_given_date_is_farther_than_sixteen_days() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new RequestDateDto(now().plusDays(16)));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/forecasts")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(forecastService, never()).getTheBestLocationForWindsurfing(anyString());
    }

    @Test
    void should_return_bad_request_status_when_given_date_is_past() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new RequestDateDto(now().minusDays(5)));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/forecasts")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(forecastService, never()).getTheBestLocationForWindsurfing(anyString());
    }

}

class MockMvcConfig {

    @Bean
    RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }

    @Bean
    ForecastService weatherService() {
        ForecastHttpClientImpl forecastHttpClientImpl = mock(ForecastHttpClientImpl.class);
        ForecastMapper forecastMapper = mock(ForecastMapper.class);
        Comparator<Forecast> comparator = mock(ForecastComparator.class);
        return new ForecastService(forecastHttpClientImpl, forecastMapper, comparator);
    }

    @Bean
    ForecastController weatherController(ForecastService forecastService) {
        return new ForecastController(forecastService);
    }
}