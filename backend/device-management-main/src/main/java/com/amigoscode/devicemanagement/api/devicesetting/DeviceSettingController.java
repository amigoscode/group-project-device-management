package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.api.verifier.AuthVerifyDevice;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingNotFoundException;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/devices/",
        produces =  "application/json",
        consumes = "application/json"
)

class DeviceSettingController {

    private final DeviceService deviceService;
    private final DeviceSettingService deviceSettingService;
    private final DeviceSettingDtoMapper deviceSettingMapper;

    @GetMapping( path = "{deviceId}/settings/{settingId}")
    @AuthVerifyDevice
    public ResponseEntity<DeviceSettingDto> getDeviceSetting(@PathVariable String deviceId, @PathVariable String settingId) {
        DeviceSetting deviceSetting = deviceSettingService.findById(settingId);
        if(!deviceSetting.isDeviceTheOwnerOfThisSetting(deviceId)){
            throw new DeviceSettingNotFoundException();
        }
        return ResponseEntity
                .ok(deviceSettingMapper.toDto(deviceSetting));
    }

    @PostMapping( path = "{deviceId}/settings")
    public ResponseEntity<DeviceSettingDto> saveDeviceSetting(@PathVariable String deviceId, @RequestBody DeviceSettingDto dto) {
        DeviceSetting deviceSetting  = deviceSettingService.save(deviceSettingMapper.toDomain(dto));
        return ResponseEntity
                .ok(deviceSettingMapper.toDto(deviceSetting));
    }

    @PutMapping( path = "{deviceId}/settings/{settingId}")
    @AuthVerifyDevice
    public ResponseEntity<Void> updateDeviceSetting(@PathVariable String deviceId, @PathVariable String settingId, @RequestBody DeviceSettingDto dto) {
        DeviceSetting deviceSetting = deviceSettingService.findById(settingId);
        if(!deviceSetting.isDeviceTheOwnerOfThisSetting(deviceId)){
            throw new DeviceSettingNotFoundException();
        }
        deviceSettingService.update(deviceSettingMapper.toDomain(dto));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{deviceId}/settings/{settingId}")
    @AuthVerifyDevice
    public ResponseEntity<Void> removeDeviceSetting(@PathVariable String deviceId, @PathVariable String settingId){
        DeviceSetting deviceSetting = deviceSettingService.findById(settingId);
        if(!deviceSetting.isDeviceTheOwnerOfThisSetting(deviceId)){
            throw new DeviceSettingNotFoundException();
        }
        deviceSettingService.removeById(settingId);
        return ResponseEntity.noContent().build();
    }

}
