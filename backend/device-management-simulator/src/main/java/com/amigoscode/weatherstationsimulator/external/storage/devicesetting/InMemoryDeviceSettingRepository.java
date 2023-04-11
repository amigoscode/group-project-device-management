package com.amigoscode.weatherstationsimulator.external.storage.devicesetting;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryDeviceSettingRepository {

    private DeviceSettingDao deviceSettingDao = new DeviceSettingDao("3", 5, true);

    DeviceSettingDao save(DeviceSettingDao dao) {
        this.deviceSettingDao.setDeviceId(dao.getDeviceId());
        this.deviceSettingDao.setMeasurementPeriod(dao.getMeasurementPeriod());
        this.deviceSettingDao.setIsMeasurementEnabled(dao.getIsMeasurementEnabled());

        return deviceSettingDao;
    }

    DeviceSettingDao get() {
        return deviceSettingDao;
    }

}
