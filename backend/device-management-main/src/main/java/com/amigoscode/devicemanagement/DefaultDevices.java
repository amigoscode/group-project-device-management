package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Log
public class DefaultDevices  implements CommandLineRunner {
    private final DeviceService deviceService;

    public DefaultDevices(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    private final Device device1 = new Device(
            "3",
            "Weather-Device3",
            "5",
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            "Zico" );
    private final Device device2 = new Device(
            "4",
            "Weather-Device4",
            "5",
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            "Mico" );



    @Override
    public void run(String... args){
        try {
            addDevice(device1);
            addDevice(device2);
        } catch (Exception ex) {
            log.warning("Devices already exist");
        }
    }

    private void addDevice(Device device){
        deviceService.save(device);
    }

}
