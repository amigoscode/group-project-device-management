package com.amigoscode.weatherstationsimulator.api.devivesetting;


import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSetting;
import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/device/settings",
        produces = "application/json",
        consumes = "application/json"
)

class DeviceSettingController {

    private final DeviceSettingService deviceSettingService;
    private final DeviceSettingDtoMapper deviceSettingDtoMapper;

    @PutMapping
    public ResponseEntity<DeviceSettingDto> updateDevice(@RequestBody DeviceSettingDto dto) {
        DeviceSetting deviceSetting = deviceSettingService.saveDeviceSetting(deviceSettingDtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(deviceSettingDtoMapper.toDto(deviceSetting));
    }

    @GetMapping
    public ResponseEntity<DeviceSettingDto> getDeviceSetting() {
        DeviceSetting deviceSetting  = deviceSettingService.getDeviceSetting();

        return ResponseEntity
                .ok(deviceSettingDtoMapper.toDto(deviceSetting));
    }

}
