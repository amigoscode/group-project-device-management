package com.amigoscode.weatherstationsimulator;

import com.amigoscode.weatherstationsimulator.domain.measurement.Location;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.Wind;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestMeasurementFactory {

    private static Long measurementSequence = 0l;

    public static Measurement createRandom() {
        measurementSequence++;

        return new Measurement(
                measurementSequence,
                "deviceId" + measurementSequence,
                24.85f + measurementSequence,
                1013.0f + measurementSequence,
                123.45f + measurementSequence,
                new Wind(2.57f + measurementSequence, 125.1f + measurementSequence),
                new Location(19.457216f + measurementSequence, 51.759445f + measurementSequence, 278.0f + measurementSequence),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC"))
        );

    }

}
