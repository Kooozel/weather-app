package com.kooozel.weatherapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kooozel.weatherapp.model.ExcelEntry;
import com.kooozel.weatherapp.model.ExcelSheetProperties;
import com.kooozel.weatherapp.model.WeatherData;
import com.kooozel.weatherapp.repository.WeatherDataDAO;
import com.kooozel.weatherapp.repository.WeatherDataRepository;
import com.kooozel.weatherapp.service.export.ExcelExporter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherDataService {
    private static final String SHEET_NAME = "Weather Data";
    private static final List<String> WEATHER_DATA_COLUMNS = List.of("Station ID", "Date Time", "Temperature", "Humidity", "Wind Speed");
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherDataDAO weatherDataDAO;
    private final ExcelExporter excelExporter;

    public void saveWeatherData(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
    }

    public byte[] exportWeatherDataToExcel(String stationId, LocalDateTime from, LocalDateTime to) {
        var weatherData = weatherDataDAO.getWeatherData(stationId, from, to);

        var dataRows = weatherData.stream()
                .map(wd -> new ExcelEntry(List.of(wd.getStationId(), wd.getDateTime().toString(),
                        String.valueOf(wd.getTemperature()), String.valueOf(wd.getHumidity()),
                        String.valueOf(wd.getWindSpeed()))))
                .toList();

        return excelExporter.create(ExcelSheetProperties.builder()
                .headerRow(new ExcelEntry(WEATHER_DATA_COLUMNS))
                .sheetName(SHEET_NAME)
                .dataRows(dataRows)
                .build());
    }

    public void createMockData() {
        weatherDataRepository.deleteAll();
        var weatherData = new ArrayList<WeatherData>();
        for (int i = 0; i < 60; i++) {
            weatherData.add(WeatherData.builder()
                    .stationId("station-1")
                    .dateTime(LocalDateTime.now().minusMinutes(i*10))
                    .temperature(Math.round((20 + Math.random() * 2 - 1) * 10.0) / 10.0)
                    .humidity(50 + Math.random() * 20)
                    .windSpeed(5 + Math.random() * 5)
                    .build());
        }
        weatherDataRepository.saveAll(weatherData);
    }

    public double getTemperaturePrediction(String stationId) {
        var historicalData = weatherDataDAO.getTemperatureData(stationId);

        return historicalData[historicalData.length - 1] + calculateDelta(historicalData);
    }

    private double calculateDelta(double[] historicalData) {
        var weights = new double[] {0.5, 0.3, 0.2};
        var weightedAverage = 0.0;
        for (int i = 0; i < historicalData.length - 1 ; i++) {
            var diff = historicalData[i] - historicalData[i + 1];
            var weight = 0.0;
            if (i < 20) {
                weight = weights[0];
            } else if (i < 40) {
                weight = weights[1];
            } else {
                weight = weights[2];
            }

            weightedAverage += diff * weight;
        }

        return Math.round((weightedAverage / historicalData.length)* 10.0) / 10.0;
    }
}