package com.amigoscode.weatherstationsimulator.config;

import com.amigoscode.weatherstationsimulator.domain.device.DeviceRepository;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSettingRepository;
import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSettingService;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementPublishing;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementRepository;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementService;
import com.amigoscode.weatherstationsimulator.domain.measurement.TakeMeasurement;
import com.amigoscode.weatherstationsimulator.external.measurement.FakeTakeMeasurementAdapter;
import com.amigoscode.weatherstationsimulator.external.mqtt.MeasurementMqttAdapter;
import com.amigoscode.weatherstationsimulator.external.mqtt.MeasurementMqttMapper;
import com.amigoscode.weatherstationsimulator.external.mqtt.MqttGateway;
import com.amigoscode.weatherstationsimulator.external.storage.device.DeviceDaoMapper;
import com.amigoscode.weatherstationsimulator.external.storage.device.DeviceStorageAdapter;
import com.amigoscode.weatherstationsimulator.external.storage.device.InMemoryDeviceRepository;
import com.amigoscode.weatherstationsimulator.external.storage.devicesetting.DeviceSettingDaoMapper;
import com.amigoscode.weatherstationsimulator.external.storage.devicesetting.DeviceSettingStorageAdapter;
import com.amigoscode.weatherstationsimulator.external.storage.devicesetting.InMemoryDeviceSettingRepository;
import com.amigoscode.weatherstationsimulator.external.storage.measurement.InMemoryMeasurementRepository;
import com.amigoscode.weatherstationsimulator.external.storage.measurement.MeasurementDaoMapper;
import com.amigoscode.weatherstationsimulator.external.storage.measurement.MeasurementStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("domain.properties")
class DomainConfiguration {

    @Bean
    public TakeMeasurement takeMeasurement(MeasurementRepository measurementRepository, DeviceService deviceService) {
        return new FakeTakeMeasurementAdapter(measurementRepository, deviceService);
    }

    public MeasurementPublishing measurementPublishing(MqttGateway mqttGateway, MeasurementMqttMapper measurementMqttMapper) {
        return new MeasurementMqttAdapter(mqttGateway, measurementMqttMapper);
    }

    @Bean
    public MeasurementRepository measurementRepository(InMemoryMeasurementRepository inMemoryMeasurementRepository, MeasurementDaoMapper measurementDaoMapper) {
        return new MeasurementStorageAdapter(inMemoryMeasurementRepository, measurementDaoMapper);
    }

    @Bean
    public MeasurementService measurementService(DeviceService deviceService, MeasurementRepository measurementRepository, TakeMeasurement takeMeasurement, MeasurementPublishing measurementPublishing) {
        return new MeasurementService(deviceService, measurementRepository, takeMeasurement, measurementPublishing);
    }

    @Bean
    public DeviceRepository deviceRepository(InMemoryDeviceRepository inMemoryDeviceRepository, DeviceDaoMapper deviceDaoMapper) {
        return new DeviceStorageAdapter(inMemoryDeviceRepository, deviceDaoMapper);
    }

    @Bean
    public DeviceService deviceService(DeviceRepository deviceRepository) {
        return new DeviceService(deviceRepository);
    }

    @Bean
    public DeviceSettingRepository deviceSettingRepository(InMemoryDeviceSettingRepository inMemoryDeviceSettingRepository, DeviceSettingDaoMapper deviceSettingDaoMapper) {
        return new DeviceSettingStorageAdapter(inMemoryDeviceSettingRepository, deviceSettingDaoMapper);
    }

    @Bean
    public DeviceSettingService deviceSettingService(DeviceSettingRepository deviceSettingRepository) {
        return new DeviceSettingService(deviceSettingRepository);
    }
}
