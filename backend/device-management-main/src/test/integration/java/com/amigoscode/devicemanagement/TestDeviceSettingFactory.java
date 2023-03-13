package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestDeviceSettingFactory {

    private static int deviceSettingSequence = 0;
    private static Boolean deviceMeasurementStatus = true;


    public static DeviceSetting createDeviceSetting() {

        deviceSettingSequence++;

        return new DeviceSetting(
                "DeviceSetting" + deviceSettingSequence,
                "DEVICE" + deviceSettingSequence,
                30,
                deviceMeasurementStatus,
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                "Updated By " + deviceSettingSequence
        );
    }
}
