package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.device.model.Device;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestDeviceFactory {

    private static int deviceSequence = 0;

    public static Device createRandom() {
        deviceSequence++;

        return new Device(
                "DEVICE" + deviceSequence,
                "Device Name " + deviceSequence,
                "Owner Id " + deviceSequence,
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                "Updated By " + deviceSequence
        );

    }

}
