package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.measurement.model.Location;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.Wind;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestMeasurementFactory {

    private static int measurementSequence = 0;

    public static Measurement createRandom() {
        measurementSequence++;

        return new Measurement(
                "MEASUREMENT" + measurementSequence,
                "deviceId" + measurementSequence,
                24.85f + measurementSequence,
                1013.0f + measurementSequence,
                123.45f + measurementSequence,
                new Wind(2.57f + measurementSequence, 125.1f + measurementSequence),
                new Location(19.457216f, 51.759445f, 278.0f + measurementSequence),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC"))
        );

    }

}
