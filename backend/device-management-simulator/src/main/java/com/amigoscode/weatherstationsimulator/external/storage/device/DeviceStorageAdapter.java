package com.amigoscode.weatherstationsimulator.external.storage.device;


import com.amigoscode.weatherstationsimulator.domain.device.Device;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Log
@Component
public
class DeviceStorageAdapter implements DeviceRepository {

    private final InMemoryDeviceRepository deviceRepository;

    private final DeviceDaoMapper mapper;

    @Override
    public Device getDevice() {
        return mapper.toDomain(deviceRepository.get());
    }

    @Override
    public Device saveDevice(final Device device) {
        return mapper.toDomain(deviceRepository.save(mapper.toDao(device)));
    }
}
