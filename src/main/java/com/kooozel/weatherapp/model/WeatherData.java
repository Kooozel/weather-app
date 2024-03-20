package com.kooozel.weatherapp.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;

@Document(collection = "weatherData")
@Builder
@Getter
public class WeatherData {
    @Id
    private String id;
    private String stationId;
    private LocalDateTime dateTime;
    private Double temperature; // in Celsius
    private Double humidity; // in percentage
    private Double windSpeed; // in km/h
}
