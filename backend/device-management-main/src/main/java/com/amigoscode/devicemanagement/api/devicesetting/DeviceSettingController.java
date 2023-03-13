package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.api.verifier.AuthVerifyDevice;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/devices/{deviceId}/settings",
        produces =  "application/json",
        consumes = "application/json"
)

class DeviceSettingController {

    private final DeviceService deviceService;
    private final DeviceSettingService deviceSettingService;
    private final DeviceSettingDtoMapper deviceSettingMapper;

    @GetMapping()
    @AuthVerifyDevice
    public ResponseEntity<DeviceSettingDto> getDeviceSetting(@PathVariable String deviceId) {
        DeviceSetting deviceSetting = deviceSettingService.findByDeviceId(deviceId);
        return ResponseEntity
                .ok(deviceSettingMapper.toDto(deviceSetting));
    }

    @PostMapping()
    public ResponseEntity<DeviceSettingDto> saveDeviceSetting(@PathVariable String deviceId, @RequestBody DeviceSettingDto dto) {
        DeviceSetting deviceSetting  = deviceSettingService.save(deviceSettingMapper.toDomain(dto));
        return ResponseEntity
                .ok(deviceSettingMapper.toDto(deviceSetting));
    }

    @PutMapping()
    @AuthVerifyDevice
    public ResponseEntity<Void> updateDeviceSetting(@PathVariable String deviceId, @RequestBody DeviceSettingDto dto) {
        deviceSettingService.update(deviceSettingMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

}