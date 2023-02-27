package com.amigoscode.weatherstationsimulator.domain.device;

public interface DeviceRepository {
    Device getDevice();

    Device saveDevice(Device device);
}
