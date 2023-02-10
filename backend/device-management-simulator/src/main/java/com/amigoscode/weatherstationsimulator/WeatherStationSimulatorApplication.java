package com.amigoscode.weatherstationsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherStationSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherStationSimulatorApplication.class, args);
    }

}
