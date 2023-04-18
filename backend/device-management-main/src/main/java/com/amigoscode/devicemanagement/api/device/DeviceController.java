package com.amigoscode.devicemanagement.api.device;


import com.amigoscode.devicemanagement.api.verifier.AuthVerifyDevice;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;

import static com.amigoscode.devicemanagement.domain.user.model.UserRole.ADMIN;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/devices",
        produces = "application/json",
        consumes = "application/json"
)

class DeviceController {

    private final DeviceService deviceService;
    private final DeviceDtoMapper deviceMapper;
    private final PageDeviceDtoMapper pageDeviceDtoMapper;
    private final UserService userService;

    @GetMapping( path = "/{deviceId}")
    @AuthVerifyDevice
    public ResponseEntity<DeviceDto> getDevice(@PathVariable String deviceId) {
        Device device = deviceService.findById(deviceId);
        return ResponseEntity
                .ok(deviceMapper.toDto(device));
    }

    @GetMapping
    public ResponseEntity<PageDeviceDto> getDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        Pageable pageable = PageRequest.of(page, size);
        PageDeviceDto pageDevices;

        if (user.getRoles().contains(ADMIN)) {
            pageDevices = pageDeviceDtoMapper.toPageDto(deviceService.findAll(pageable));
        }
        else {
            pageDevices = pageDeviceDtoMapper.toPageDto(deviceService.findAllByOwnerId(pageable, user.getId()));
        }


        return ResponseEntity.ok(pageDevices);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> saveDevice(@RequestBody DeviceDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        Device device = deviceService.save(deviceMapper.toDomain(dto), user.getId());
        return ResponseEntity
                .ok(deviceMapper.toDto(device));
    }

    @PutMapping( path = "/{deviceId}")
    @AuthVerifyDevice
    public ResponseEntity<Void> updateDevice(@PathVariable String deviceId, @RequestBody DeviceDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        deviceService.update(deviceMapper.toDomain(dto), user.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{deviceId}")
    @AuthVerifyDevice
    public ResponseEntity<Void> removeDevice(@PathVariable String deviceId){
        deviceService.removeById(deviceId);
        return ResponseEntity.noContent().build();
    }

}
