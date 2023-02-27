package com.amigoscode.weatherstationsimulator.api.device;


import com.amigoscode.weatherstationsimulator.domain.device.Device;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/device",
        produces = "application/json",
        consumes = "application/json"
)

class DeviceController {

    private final DeviceService deviceService;
    private final DeviceDtoMapper deviceDtoMapper;

    @PutMapping
    public ResponseEntity<DeviceDto> updateDevice(@RequestBody DeviceDto dto) {
        Device device = deviceService.saveDevice(deviceDtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(deviceDtoMapper.toDto(device));
    }

//    @GetMapping
//    public ResponseEntity<Device> getDevice() {
//        Device device = deviceService.getDevice();
//
//        return ResponseEntity
//                .ok(device);
//    }

    @GetMapping
    public ResponseEntity<DeviceDto> getDevice() {
        Device device = deviceService.getDevice();

        return ResponseEntity
                .ok(deviceDtoMapper.toDto(device));
    }

}
