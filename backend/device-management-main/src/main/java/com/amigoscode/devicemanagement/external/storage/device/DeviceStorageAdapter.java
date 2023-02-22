package com.amigoscode.devicemanagement.external.storage.device;

import com.amigoscode.devicemanagement.domain.device.DeviceRepository;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.device.model.PageDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
@Component
class DeviceStorageAdapter implements DeviceRepository {

    private final MongoDeviceRepository deviceRepository;

    private final DeviceEntityMapper mapper;

    @Override
    public Device save(Device device){
        try{
            DeviceEntity saved = deviceRepository.insert(mapper.toEntity(device));
            log.info("Saved entity" + saved);
            return mapper.toDomain(saved);
        } catch (DuplicateKeyException ex) {
            log.warning("Device " + device.getName() + "already exists in db");
            throw new DeviceAlreadyExistsException();

        }
    }

    @Override
    public void update(Device device) {
        deviceRepository.findById(device.getId()).ifPresent(deviceEntity -> deviceRepository.save(mapper.toEntity(device)));
    }

    @Override
    public void remove(String id) {
        deviceRepository.findById(id).ifPresent(deviceEntity -> deviceRepository.deleteById(id));
    }

    @Override
    public Optional<Device> findById(String id) {
        return deviceRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Device> findByName(String name) {
        return deviceRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public PageDevice findAll(Pageable pageable) {
        Page<DeviceEntity> pageOfDevicesEntity = deviceRepository.findAll(pageable);
        List<Device> devicesOnCurrentPage = pageOfDevicesEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageDevice(
                devicesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfDevicesEntity.getTotalPages(),
                pageOfDevicesEntity.getTotalElements()
        );
    }

}
