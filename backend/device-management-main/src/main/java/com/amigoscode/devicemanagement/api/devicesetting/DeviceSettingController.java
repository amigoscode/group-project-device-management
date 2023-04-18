package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.api.verifier.AuthVerifyDevice;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    @GetMapping()
    @AuthVerifyDevice
    public ResponseEntity<DeviceSettingDto> getDeviceSetting(@PathVariable String deviceId) {
        DeviceSetting deviceSetting = deviceSettingService.findByDeviceId(deviceId);
        return ResponseEntity
                .ok(deviceSettingMapper.toDto(deviceSetting));
    }

    @PutMapping()
    @AuthVerifyDevice
    public ResponseEntity<Void> updateDeviceSetting(@PathVariable String deviceId, @RequestBody DeviceSettingDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        deviceSettingService.update(deviceSettingMapper.toDomain(dto), user.getId());
        return ResponseEntity.ok().build();
    }

}