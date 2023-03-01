package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.device.DeviceNotFoundException;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class DeviceSettingService {

    private final DeviceSettingRepository deviceSettingRepository;

    //update a device setting
    public void update(DeviceSetting deviceSetting){
        deviceSettingRepository.update(deviceSetting);
    }


    public DeviceSetting findById(String id){
        return deviceSettingRepository.findById(id)
                .orElseThrow(DeviceNotFoundException::new);
    }

    public DeviceSetting findBydeviceId(String deviceId){
        return deviceSettingRepository.findByDeviceId(deviceId)
                .orElseThrow(DeviceNotFoundException::new);
    }


}
