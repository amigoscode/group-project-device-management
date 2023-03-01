package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceSettingService {

    private final DeviceSettingRepository deviceSettingRepository;

    public DeviceSetting save(DeviceSetting deviceSetting){
        return deviceSettingRepository.save(deviceSetting);
    }

    public void update(DeviceSetting deviceSetting){
        deviceSettingRepository.update(deviceSetting);
    }

    public void removeById(String id){
        deviceSettingRepository.remove(id);
    }

    public DeviceSetting findById(String id){
        return deviceSettingRepository.findById(id)
                .orElseThrow(DeviceSettingNotFoundException::new);
    }

    public DeviceSetting findByDeviceId(String ddviceId){
        return deviceSettingRepository.findById(ddviceId)
                .orElseThrow(DeviceSettingNotFoundException::new);
    }


    public DeviceSetting findBydeviceId(String deviceId){
        return deviceSettingRepository.findByDeviceId(deviceId)
                .orElseThrow(DeviceSettingNotFoundException::new);
    }


}
