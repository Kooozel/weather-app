package com.kooozel.weatherapp.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kooozel.weatherapp.model.WeatherData;
import com.kooozel.weatherapp.service.WeatherDataService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/weather-data")
@RequiredArgsConstructor
public class WeatherDataController {
    private final WeatherDataService weatherDataService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveWeatherData(@RequestParam String stationId, @RequestParam double temperature,
            @RequestParam double humidity, @RequestParam double windSpeed) {

    var weatherData = WeatherData.builder()
            .stationId(stationId)
            .dateTime(LocalDateTime.now())
            .temperature(temperature)
            .humidity(humidity)
            .windSpeed(windSpeed)
            .build();
        weatherDataService.saveWeatherData(weatherData);
        return ResponseEntity.ok().

    build();
}
}
