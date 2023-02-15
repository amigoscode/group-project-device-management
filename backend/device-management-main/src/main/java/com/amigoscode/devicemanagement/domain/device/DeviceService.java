package com.amigoscode.devicemanagement.domain.device;

import com.amigoscode.devicemanagement.domain.device.exception.DeviceNotFoundException;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.device.model.PageDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;


    public Device save(Device device){
        return deviceRepository.save(device);
    }

    public void update(Device device){
        deviceRepository.update(device);
    }

    public void removeById(String id){
        deviceRepository.remove(id);
    }

    public Device findById(String id){
        return deviceRepository.findById(id)
                .orElseThrow(DeviceNotFoundException::new);
    }

    public PageDevice findAll(Pageable pageable){
        return deviceRepository.findAll(pageable);
    }

}
