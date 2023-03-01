package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.device.model.DeviceSetting;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestDeviceSettingFactory {

    private static int deviceSettingSequence = 0;
    private static boolean deviceMeasurementStatus = true;

    public static Device createRandom() {
        deviceSequence++;

        return new DeviceSetting(
                "Device Setting " + deviceSequence,
                "Device Id " + deviceSequence,
                "Measurement Period " + deviceSequence,
                "Device Enabled" + deviceSettingStatus,
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                "Updated By " + deviceSequence
        );

    }

}