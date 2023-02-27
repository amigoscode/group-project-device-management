package com.amigoscode.weatherstationsimulator;

import com.amigoscode.weatherstationsimulator.domain.measurement.Location;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementService;
import com.amigoscode.weatherstationsimulator.domain.measurement.Wind;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Log
public class DefaultMeasurements implements CommandLineRunner {

    private final MeasurementService measurementService;

    public DefaultMeasurements(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    private final Measurement measurement = new Measurement(
            0,
            "deviceId",
            24.85f,
            1013.0f,
            123.45f,
            new Wind(2.57f, 125.1f),
            new Location(19.457216f, 51.759445f, 278.0f),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC"))
    );

    @Override
    public void run(String... args) {
        try {
            addMeasurement(measurement);
        } catch (Exception ex) {
            log.warning("Measurements already exist");
        }
    }

    private void addMeasurement(Measurement measurement) {
        measurementService.save(measurement);
    }
}
