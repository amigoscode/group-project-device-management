package com.amigoscode.devicemanagement.api.device;


import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping( path = "/{id}")
    public ResponseEntity<DeviceDto> getDevice(@PathVariable String id) {
        Device device = deviceService.findById(id);
        return ResponseEntity
                .ok(deviceMapper.toDto(device));
    }

    @GetMapping
    public ResponseEntity<PageDeviceDto> getDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageDeviceDto pageDevices = pageDeviceDtoMapper.toPageDto(deviceService.findAll(pageable));

        return ResponseEntity.ok(pageDevices);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> saveDevice(@RequestBody DeviceDto dto) {
        Device device = deviceService.save(deviceMapper.toDomain(dto));
        return ResponseEntity
                .ok(deviceMapper.toDto(device));
    }

    @PutMapping
    public ResponseEntity<Void> updateDevice(@RequestBody DeviceDto dto) {
        deviceService.update(deviceMapper.toDomain(dto));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{deviceId}")
    public ResponseEntity<Void> removeDevice(@PathVariable String deviceId){
        deviceService.removeById(deviceId);
        return ResponseEntity.noContent().build();
    }

}
