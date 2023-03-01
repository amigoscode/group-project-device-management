package com.amigoscode.devicemanagement.api.measurement;


import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.TestMeasurementFactory;
import com.amigoscode.devicemanagement.TestUserFactory;
import com.amigoscode.devicemanagement.api.response.ErrorResponse;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementService;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeasurementControllerIT extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    MeasurementService measurementService;

    @Autowired
    MeasurementDtoMapper measurementDtoMapper;

    @Autowired
    PageMeasurementDtoMapper pageMeasurementDtoMapper;

    @Test
    void admin_should_be_able_to_get_information_about_measurement() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);
        Measurement measurement = TestMeasurementFactory.createRandom();
        measurement.setDeviceId(device.getId());
        measurementService.save(measurement);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/measurements/" + measurement.getId(),
                adminAccessToken,
                null,
                MeasurementDto.class);

        //then
        MeasurementDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareMeasurements(measurement, measurementDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_be_able_to_get_information_about_measurement_from_device_he_owns() {
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        device.setOwnerId(user.getId());
        userService.save(user);
        deviceService.save(device);
        Measurement measurement = TestMeasurementFactory.createRandom();
        measurement.setDeviceId(device.getId());
        measurementService.save(measurement);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/measurements/" + measurement.getId(),
                token,
                null,
                MeasurementDto.class);

        //then
        MeasurementDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareMeasurements(measurement, measurementDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_not_be_able_to_get_information_about_measurement_from_device_he_does_not_own() {
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        device.setOwnerId(user.getId() + "qwerty");
        userService.save(user);
        deviceService.save(device);
        Measurement measurement = TestMeasurementFactory.createRandom();
        measurement.setDeviceId(device.getId());
        measurementService.save(measurement);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/measurements/" + measurement.getId(),
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }


    @Test
    void admin_should_be_able_to_get_information_about_measurements_from_all_devices() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);
        Measurement measurement1 = TestMeasurementFactory.createRandom();
        Measurement measurement2 = TestMeasurementFactory.createRandom();
        Measurement measurement3 = TestMeasurementFactory.createRandom();
        measurement1.setDeviceId(device.getId());
        measurement2.setDeviceId(device.getId());
        measurement3.setDeviceId(device.getId());
        measurementService.save(measurement1);
        measurementService.save(measurement2);
        measurementService.save(measurement3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/measurements",
                adminAccessToken,
                null,
                PageMeasurementDto.class);

        //then
        PageMeasurementDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        compareMeasurements(measurement1, measurementDtoMapper.toDomain(body.getMeasurements().get(0)));
        compareMeasurements(measurement2, measurementDtoMapper.toDomain(body.getMeasurements().get(1)));
        compareMeasurements(measurement3, measurementDtoMapper.toDomain(body.getMeasurements().get(2)));
    }

    @Test
    void device_owner_should_be_able_to_get_information_about_measurements_from_all_devices_he_owns() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        device.setOwnerId(user.getId());
        userService.save(user);
        deviceService.save(device);
        Measurement measurement1 = TestMeasurementFactory.createRandom();
        Measurement measurement2 = TestMeasurementFactory.createRandom();
        Measurement measurement3 = TestMeasurementFactory.createRandom();
        measurement1.setDeviceId(device.getId());
        measurement2.setDeviceId(device.getId());
        measurement3.setDeviceId(device.getId());
        measurementService.save(measurement1);
        measurementService.save(measurement2);
        measurementService.save(measurement3);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/measurements",
                token,
                null,
                PageMeasurementDto.class);

        //then
        PageMeasurementDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        compareMeasurements(measurement1, measurementDtoMapper.toDomain(body.getMeasurements().get(0)));
        compareMeasurements(measurement2, measurementDtoMapper.toDomain(body.getMeasurements().get(1)));
        compareMeasurements(measurement3, measurementDtoMapper.toDomain(body.getMeasurements().get(2)));
    }

    @Test
    void device_owner_should_not_be_able_to_get_information_about_measurements_from_device_he_does_not_own() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        device.setOwnerId(user.getId() + "qwerty");
        userService.save(user);
        deviceService.save(device);
        Measurement measurement1 = TestMeasurementFactory.createRandom();
        Measurement measurement2 = TestMeasurementFactory.createRandom();
        Measurement measurement3 = TestMeasurementFactory.createRandom();
        measurement1.setDeviceId(device.getId());
        measurement2.setDeviceId(device.getId());
        measurement3.setDeviceId(device.getId());
        measurementService.save(measurement1);
        measurementService.save(measurement2);
        measurementService.save(measurement3);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/measurements",
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    private void compareMeasurements(Measurement model, Measurement tested) {
        Assertions.assertNotNull(tested);
        assertEquals(model.getId(), tested.getId());
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getTemperature(), tested.getTemperature());
        assertEquals(model.getPressure(), tested.getPressure());
        assertEquals(model.getHumidity(), tested.getHumidity());
        assertEquals(model.getWind(), tested.getWind());
        assertEquals(model.getLocation(), tested.getLocation());
        assertEquals(model.getTimestamp().toLocalDateTime(), tested.getTimestamp().toLocalDateTime());
    }

}
