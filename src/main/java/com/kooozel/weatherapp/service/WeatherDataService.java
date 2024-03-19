package com.kooozel.weatherapp.service;

import java.time.LocalDateTime;
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
}