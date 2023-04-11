package com.amigoscode.weatherstationsimulator.external.storage.devicesetting;


import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSetting;
import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Log
@Component
public
class DeviceSettingStorageAdapter implements DeviceSettingRepository {

    private final InMemoryDeviceSettingRepository deviceSettingRepository;

    private final DeviceSettingDaoMapper mapper;

    @Override
    public DeviceSetting getDeviceSetting() {
        return mapper.toDomain(deviceSettingRepository.get());
    }

    @Override
    public DeviceSetting saveDeviceSetting(final DeviceSetting deviceSetting) {
        return mapper.toDomain(deviceSettingRepository.save(mapper.toDao(deviceSetting)));
    }
}
