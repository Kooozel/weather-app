package com.kozeltech.weatherapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kozeltech.weatherapp.model.WeatherData;

public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {

}
