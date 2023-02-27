package com.wd.weatherservice.controller;

import com.wd.weatherservice.dto.RequestDateDto;
import com.wd.weatherservice.model.Forecast;
import com.wd.weatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping
    List<Forecast> getTheBestLocationForWindsurfing(@RequestBody @Valid RequestDateDto requestDateDto) {
        return weatherService.getTheBestLocationForWindsurfing(requestDateDto.date().toString());
    }

}
