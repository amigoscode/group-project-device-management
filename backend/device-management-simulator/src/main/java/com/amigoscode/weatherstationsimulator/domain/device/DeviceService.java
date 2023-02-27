package com.amigoscode.weatherstationsimulator.domain.device;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public Device saveDevice(Device device) {
        return deviceRepository.saveDevice(device);
    }

    public Device getDevice() {
        return deviceRepository.getDevice();
    }

}
