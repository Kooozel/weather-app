package com.kooozel.weatherapp.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kooozel.weatherapp.model.WeatherData;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WeatherDataDAO {
    private final MongoTemplate mongoTemplate;

    public List<WeatherData> getWeatherData(String stationId, LocalDateTime from, LocalDateTime to) {
        var query = new Query();

        if (stationId != null) {
            query.addCriteria(where("stationId").is(stationId));
        }

        if (from != null) {
            query.addCriteria(where("dateTime").gte(from).lte(to != null ? to : LocalDateTime.now()));
        }

        return mongoTemplate.find(query, WeatherData.class);
    }
}