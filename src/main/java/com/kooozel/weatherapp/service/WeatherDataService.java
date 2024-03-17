package com.kooozel.weatherapp.service;

import org.springframework.stereotype.Service;

import com.kooozel.weatherapp.model.WeatherData;
import com.kooozel.weatherapp.repository.WeatherDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherDataService {
    private final WeatherDataRepository weatherDataRepository;

    public void saveWeatherData(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
    }
}
