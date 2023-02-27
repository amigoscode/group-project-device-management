package com.amigoscode.weatherstationsimulator.external.storage.device;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryDeviceRepository {

    private DeviceDao deviceDao = new DeviceDao("3", "ownerId", "deviceName");

    DeviceDao save(DeviceDao dao) {
        this.deviceDao.setDeviceId(dao.getDeviceId());
        this.deviceDao.setOwnerId(dao.getOwnerId());
        this.deviceDao.setName(dao.getName());

        return deviceDao;
    }

    DeviceDao get() {
        return deviceDao;
    }

}
