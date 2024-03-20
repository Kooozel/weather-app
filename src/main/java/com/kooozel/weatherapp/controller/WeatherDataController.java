package com.kooozel.weatherapp.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kooozel.weatherapp.model.WeatherData;
import com.kooozel.weatherapp.service.WeatherDataService;
import com.kooozel.weatherapp.service.WeatherDataSimulation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/weather-data")
@RequiredArgsConstructor
@Slf4j
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
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportWeatherDataToExcel(@RequestParam(required = false) String stationId,
            @RequestParam(required = false) LocalDateTime from, @RequestParam(required = false) LocalDateTime to) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=weather-data.xlsx")
                .body(weatherDataService.exportWeatherDataToExcel(stationId, from, to));
    }

    @GetMapping("/create-mock-data")
    public ResponseEntity<Void> createMockData() {
        weatherDataService.createMockData();
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/temperature-prediction")
    public ResponseEntity<String> getTemperaturePrediction(@RequestParam(defaultValue = "station-1") String stationId) {
        var predictedTemperature = weatherDataService.getTemperaturePrediction(stationId);
        return ResponseEntity.ok()
                .body("Temperature prediction for " + stationId + " is " + predictedTemperature);
    }

    @GetMapping("/simulate")
    public ResponseEntity<Void> simulate(@RequestParam int numberOfStations) {
        WeatherDataSimulation.manageSimulation(numberOfStations);
        return ResponseEntity.ok()
                .build();
    }
}
