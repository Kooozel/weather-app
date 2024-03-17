package com.kooozel.weatherapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kooozel.weatherapp.model.WeatherData;

public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
}
