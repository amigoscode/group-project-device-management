package com.amigoscode.weatherstationsimulator;

import com.amigoscode.weatherstationsimulator.domain.device.Device;

public class TestDeviceFactory {

    private static int deviceSequence = 0;

    public static Device createRandom() {
        deviceSequence++;

        return new Device(
                "deviceId" + deviceSequence,
                "ownerId" + deviceSequence,
                "deviceName" + deviceSequence
        );

    }

}
