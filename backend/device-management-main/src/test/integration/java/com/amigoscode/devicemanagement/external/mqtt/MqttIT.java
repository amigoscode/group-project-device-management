package com.amigoscode.devicemanagement.external.mqtt;


import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.TestMeasurementFactory;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementService;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.PageMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttIT extends BaseIT {

    @Autowired
    MeasurementService measurementService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    MeasurementMqttMapper measurementMqttMapper;


    @Test
    public void mqtt_client_should_be_able_to_publish_message() {

        publishMqttMessage("abc", "MQTT!");

    }

    @Test
    public void the_measurement_published_in_the_right_topic_by_the_registered_device_should_be_saved_in_the_repository() throws Exception {
        //given
        Device device = TestDeviceFactory.createDevice();
        deviceService.save(device, "creatorId");
        MeasurementMqttDto measurement = measurementMqttMapper.toMqtt(TestMeasurementFactory.createRandom());
        measurement.setDeviceId(device.getId());

        //when
        publishMqttMessage("dm/measurements", measurement);

        //then
        TimeUnit.SECONDS.sleep(1);
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        String deviceId = device.getId();
        PageMeasurement pageWithFoundMeasurements = measurementService.findAllByDeviceId(pageable, deviceId);
        assertEquals(1, pageWithFoundMeasurements.getTotalElements());
        compareMeasurements(measurementMqttMapper.toDomain(measurement), pageWithFoundMeasurements.getMeasurements().get(0));
    }

    @Test
    public void the_measurement_published_in_the_inappropriate_topic_by_the_registered_device_should_not_be_saved_in_the_repository() throws Exception {
        //given
        Device device = TestDeviceFactory.createDevice();
        deviceService.save(device, "creatorId");
        MeasurementMqttDto measurement = measurementMqttMapper.toMqtt(TestMeasurementFactory.createRandom());
        measurement.setDeviceId(device.getId());

        //when
        publishMqttMessage("dm/wrongTopic", measurement);

        //then
        TimeUnit.SECONDS.sleep(1);
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        String deviceId = device.getId();
        PageMeasurement pageWithFoundMeasurements = measurementService.findAllByDeviceId(pageable, deviceId);
        assertEquals(0, pageWithFoundMeasurements.getTotalElements());
    }

    @Test
    public void the_measurement_published_in_the_right_topic_by_the_unregistered_device_should_not_be_saved_in_the_repository() throws Exception {
        //given
        Device device = TestDeviceFactory.createDevice();
        MeasurementMqttDto measurement = measurementMqttMapper.toMqtt(TestMeasurementFactory.createRandom());

        //when
        publishMqttMessage("dm/measurements", measurement);

        //then
        TimeUnit.SECONDS.sleep(1);
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        String deviceId = device.getId();
        PageMeasurement pageWithFoundMeasurements = measurementService.findAllByDeviceId(pageable, deviceId);
        assertEquals(0, pageWithFoundMeasurements.getTotalElements());
    }

    private void compareMeasurements(Measurement model, Measurement tested) {
        Assertions.assertNotNull(tested);
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getTemperature(), tested.getTemperature());
        assertEquals(model.getPressure(), tested.getPressure());
        assertEquals(model.getHumidity(), tested.getHumidity());
        assertEquals(model.getWind(), tested.getWind());
        assertEquals(model.getLocation(), tested.getLocation());
        assertEquals(model.getTimestamp().toLocalDateTime(), tested.getTimestamp().toLocalDateTime());
    }

}

