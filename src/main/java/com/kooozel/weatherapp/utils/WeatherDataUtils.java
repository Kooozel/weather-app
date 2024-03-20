package com.kooozel.weatherapp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WeatherDataUtils {

    public static Double round(Double value, int places) {
        Double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static Double generateTemperature() {
        return round((20 + Math.random() * 2 - 1), 2);
    }

    public static Double generateHumidity() {
        return round(50 + Math.random() * 20, 2);
    }

    public static Double generateWindSpeed() {
        return round(5 + Math.random() * 5,2);
    }

    public static double calculateDelta(double[] historicalData) {
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

        return round((weightedAverage / historicalData.length), 2);
    }
}
