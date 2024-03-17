package com.kooozel.weatherapp.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Document(collection = "weatherData")
@Builder
public class WeatherData {
    @Id
    private String id;
    private String stationId;
    private LocalDateTime dateTime;
    private double temperature; // in Celsius
    private double humidity; // in percentage
    private double windSpeed; // in km/h
}
