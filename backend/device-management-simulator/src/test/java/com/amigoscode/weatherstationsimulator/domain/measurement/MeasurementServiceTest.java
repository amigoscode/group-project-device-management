package com.amigoscode.weatherstationsimulator.domain.measurement;


import com.amigoscode.weatherstationsimulator.domain.device.Device;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

    @Mock
    private DeviceService deviceService;

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private TakeMeasurement takeMeasurement;

    @Mock
    private MeasurementPublishing measurementPublishing;

    @InjectMocks
    private MeasurementService measurementService;

    private final Device fakeDevice = new Device(
            "deviceId",
            "ownerId",
            "name"
    );

    private final Measurement fakeMeasurement = new Measurement(
            1l,
            "deviceId123",
            24.85f,
            1013.0f,
            123.45f,
            new Wind(2.57f, 125.1f),
            new Location(19.457216f, 51.759445f, 278.0f),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC"))
    );

    @Test
    void save_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> measurementService.save(fakeMeasurement));
    }

    @Test
    void remove_by_id_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> measurementService.removeById(fakeMeasurement.getId()));
    }

    @Test
    void save_method_should_return_saved_measurement() {
        Mockito.when(measurementRepository.save(
                fakeMeasurement
        )).thenReturn(fakeMeasurement.getId());

        //when
        Long returnedId = measurementService.save(fakeMeasurement);

        //then
        assertEquals(fakeMeasurement.getId(), returnedId);
    }

    @Test
    void find_by_id_method_should_return_founded_measurement_when_measurement_exist() {
        Mockito.when(measurementRepository.findById(fakeMeasurement.getId())).thenReturn(Optional.of(fakeMeasurement));

        //when
        Measurement foundMeasurement = measurementService.findById(fakeMeasurement.getId());

        //then
        compareMeasurements(fakeMeasurement, foundMeasurement);
    }

    @Test
    void find_by_id_method_should_throw_measurement_not_found_exception_when_measurement_does_not_exist() {
        Mockito.when(measurementRepository.findById(fakeMeasurement.getId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(MeasurementNotFoundException.class,
                ()-> measurementService.findById(fakeMeasurement.getId()));
    }

    @Test
    void find_all_method_should_return_all_found_measurements() {
        Mockito.when(measurementRepository.findAll()).thenReturn(List.of(fakeMeasurement));

        //when
        List<Measurement> foundMeasurements = measurementService.findAll();

        //then
        Assertions.assertNotNull(foundMeasurements);
        compareMeasurements(fakeMeasurement, foundMeasurements.get(0));
    }

    @Test
    void take_and_publish_measurement_method_should_publish_measurement_with_proper_device_id_if_measurement_is_not_empty(){
        Mockito.when(deviceService.getDevice()).thenReturn(fakeDevice);
        Mockito.when(takeMeasurement.getResult()).thenReturn(Optional.of(fakeMeasurement));

        //when
        final Optional<Measurement> publishedMeasurement = measurementService.takeAndPublishMeasurement();

        //then
        verify(measurementPublishing).publish(fakeMeasurement);
        assertEquals(publishedMeasurement.get().getDeviceId(), fakeDevice.getId());
    }

    @Test
    void take_and_publish_measurement_method_should_not_publish_measurement_if_it_is_empty(){
        Mockito.when(takeMeasurement.getResult()).thenReturn(Optional.empty());

        //when
        measurementService.takeAndPublishMeasurement();

        //then
        verify(measurementPublishing, never()).publish(fakeMeasurement);
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