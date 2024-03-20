package com.kozeltech.weatherapp.service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kozeltech.weatherapp.utils.WeatherDataUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Getter
public class WeatherDataSimulation {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static boolean running = false;

    public static void manageSimulation(int numberOfStations) {
        if (!running) {
            startSimulation(numberOfStations);
        } else {
            stopSimulation();
        }
    }

    private static void startSimulation(int numberOfStations) {
        log.info("Starting weather data simulation for {} stations", numberOfStations);
        running = true;
        var executorService = Executors.newFixedThreadPool(numberOfStations);
        for (int i = 0; i < numberOfStations; i++) {
            executorService.submit(new WeatherStationRunnable("station-" + (i + 1)));
        }
    }

    private static void stopSimulation() {
        running = false;
        log.info("Simulation stopped.");
    }

    private record WeatherStationRunnable(String stationId) implements Runnable {

        @Override
        public void run() {
            while (running) {
                HttpHeaders headers = new HttpHeaders();

                MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
                map.add("stationId", stationId);
                map.add("temperature", String.valueOf(WeatherDataUtils.generateTemperature()));
                map.add("humidity", String.valueOf(WeatherDataUtils.generateHumidity()));
                map.add("windSpeed", String.valueOf(WeatherDataUtils.generateWindSpeed()));

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

                var url = "http://localhost:8080/api/v1/weather-data/save";
                restTemplate.postForEntity(url, request, Void.class);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
