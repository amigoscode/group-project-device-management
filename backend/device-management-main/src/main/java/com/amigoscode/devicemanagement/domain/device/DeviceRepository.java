package com.amigoscode.devicemanagement.domain.device;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.device.model.PageDevice;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DeviceRepository {

    Device save(Device device);

    void update(Device device);

    void remove(String id);

    Optional<Device> findById(String id);

    Optional<Device> findByName(String name);

    PageDevice findAll(Pageable pageable);

    PageDevice findAllByOwnerId(Pageable pageable, String userId);
}
