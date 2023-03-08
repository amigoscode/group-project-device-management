package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/devices/{deviceId}/settings",
        produces =  "application/json",
        consumes = "application/json"
)

class DeviceSettingController {

    private final DeviceSettingService deviceSettingService;

    private final DeviceSettingDtoMapper deviceSettingMapper;

    private final UserService userService;
}
