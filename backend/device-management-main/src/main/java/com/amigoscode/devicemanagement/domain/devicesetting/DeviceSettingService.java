package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceSettingService {

    private final DeviceSettingRepository deviceSettingRepository;
    private final DeviceSettingPublisher deviceSettingPublisher;

    public DeviceSetting save(DeviceSetting deviceSetting){
        if (deviceSettingRepository.findByDeviceId(deviceSetting.getDeviceId()).isPresent()) {
            throw new DeviceSettingAlreadyExistsException();
        }
        return deviceSettingRepository.save(deviceSetting);
    }

    public void update(DeviceSetting deviceSetting){
        deviceSettingRepository.update(deviceSetting);
        publish(deviceSetting);
    }

    public void removeById(String id) {
        deviceSettingRepository.remove(id);
    }

    public DeviceSetting findById(String id){
        return deviceSettingRepository.findById(id)
                .orElseThrow(DeviceSettingNotFoundException::new);
    }

    public DeviceSetting findByDeviceId(String deviceId){
        return deviceSettingRepository.findByDeviceId(deviceId)
                .orElseThrow(DeviceSettingNotFoundException::new);
    }

    public void publish(DeviceSetting deviceSetting) {
        deviceSettingPublisher.publish(deviceSetting);
    }
}
