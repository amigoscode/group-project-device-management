package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class DeviceSettingService {

    private final DeviceSettingRepository deviceSettingRepository;
    private final DeviceSettingPublisher deviceSettingPublisher;
    private final Clock clock;

    public DeviceSetting save(DeviceSetting deviceSetting, String creatorId){
        if (deviceSettingRepository.findByDeviceId(deviceSetting.getDeviceId()).isPresent()) {
            throw new DeviceSettingAlreadyExistsException();
        }
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        deviceSetting.setCreatedAt(createdAt);
        deviceSetting.setUpdatedBy(creatorId);
        return deviceSettingRepository.save(deviceSetting);
    }

    public void update(DeviceSetting deviceSetting, String updaterId){
        deviceSetting.setUpdatedAt(ZonedDateTime.now(clock));
        deviceSetting.setUpdatedBy(updaterId);
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
