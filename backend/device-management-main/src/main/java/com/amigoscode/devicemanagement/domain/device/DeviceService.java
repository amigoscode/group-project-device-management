package com.amigoscode.devicemanagement.domain.device;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.device.model.PageDevice;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceSettingService deviceSettingService;
    private final Clock clock;

    public boolean isDeviceRegistered(String deviceId){
        return deviceRepository.findById(deviceId).isPresent();
    }

    public Device save(Device device, String creatorId){
        if ( (device.getId() != null) && (deviceRepository.findById(device.getId()).isPresent()) ) {
            throw new DeviceAlreadyExistsException();
        }
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        device.setCreatedAt(createdAt);
        device.setUpdatedBy(creatorId);
        Device savedDevice = deviceRepository.save(device);
        DeviceSetting defaaultDeviceSetting = new DeviceSetting(
          null,
                savedDevice.getId(),
                5,
                true,
                createdAt,
                null,
                null,
                creatorId
        );
        deviceSettingService.save(defaaultDeviceSetting);

        return savedDevice;
    }

    public void update(Device device, String updaterId){
        device.setUpdatedAt(ZonedDateTime.now(clock));
        device.setUpdatedBy(updaterId);
        deviceRepository.update(device);
    }

    public void removeById(String id){
        DeviceSetting deviceSetting = deviceSettingService.findByDeviceId(id);
        deviceSettingService.removeById(deviceSetting.getId());
        deviceRepository.remove(id);
    }

    public Device findById(String id){
        return deviceRepository.findById(id)
                .orElseThrow(DeviceNotFoundException::new);
    }

    public Device findByName(String name){
        return deviceRepository.findByName(name)
                .orElseThrow(DeviceNotFoundException::new);
    }

    public PageDevice findAll(Pageable pageable){
        return deviceRepository.findAll(pageable);
    }

    public PageDevice findAllByOwnerId(final Pageable pageable, final String userId) {
        return deviceRepository.findAllByOwnerId(pageable, userId);
    }
}
