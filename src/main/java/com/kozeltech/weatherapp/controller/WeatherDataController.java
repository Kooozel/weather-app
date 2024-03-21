package com.kozeltech.weatherapp.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kozeltech.weatherapp.model.WeatherData;
import com.kozeltech.weatherapp.service.WeatherDataService;
import com.kozeltech.weatherapp.service.WeatherDataSimulation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/weather-data")
@RequiredArgsConstructor
@Slf4j
public class WeatherDataController {
    private final WeatherDataService weatherDataService;

    @Operation(summary = "Save weather data")
    @PostMapping("/save")
    public ResponseEntity<Void> saveWeatherData(@Parameter(description = "Id of the weather station") @RequestParam String stationId,
            @Parameter(description = "Temperature in Celsius") @RequestParam double temperature,
            @Parameter(description = "Humidity in percents") @RequestParam double humidity,
            @Parameter(description = "Wind speed in km/h") @RequestParam double windSpeed) {

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

    @Operation(summary = "Export weather data to Excel")
    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportWeatherDataToExcel(
            @Parameter(description = "Id of the weather station", allowEmptyValue = true) @RequestParam(required = false) String stationId,
            @Parameter(description = "Date from", allowEmptyValue = true) @RequestParam(required = false) LocalDateTime from,
            @Parameter(description = "Date to", allowEmptyValue = true) @RequestParam(required = false) LocalDateTime to) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=weather-data.xlsx")
                .body(weatherDataService.exportWeatherDataToExcel(stationId, from, to));
    }

    @Operation(summary = "Wipes out data and create mock data")
    @GetMapping("/create-mock-data")
    public ResponseEntity<Void> createMockData() {
        weatherDataService.createMockData();
        return ResponseEntity.ok()
                .build();
    }

    @Operation(summary = "Get temperature prediction")
    @GetMapping("/temperature-prediction")
    public ResponseEntity<String> getTemperaturePrediction(
            @Parameter(description = "Id of the weather station") @RequestParam(defaultValue = "station-1") String stationId) {
        var predictedTemperature = weatherDataService.getTemperaturePrediction(stationId);
        return ResponseEntity.ok()
                .body("Temperature prediction for " + stationId + " is " + predictedTemperature);
    }

    @Operation(summary = "Simulate weather data, for testing purposes. First request will start simulation, second will stop simulation")
    @GetMapping("/simulate")
    public ResponseEntity<Void> simulate(
            @Parameter(description = "Number of weather station to simulate") @RequestParam int numberOfStations) {
        WeatherDataSimulation.manageSimulation(numberOfStations);
        return ResponseEntity.ok()
                .build();
    }
}
