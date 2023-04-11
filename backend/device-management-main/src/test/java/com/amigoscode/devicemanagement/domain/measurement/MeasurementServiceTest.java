package com.amigoscode.devicemanagement.domain.measurement;

import com.amigoscode.devicemanagement.domain.measurement.model.Location;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.Wind;
import com.amigoscode.devicemanagement.external.storage.measurement.MeasurementAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private MeasurementService measurementService;

    private final Measurement fakeMeasurement = new Measurement(
            "deviceId",
            24.85f,
            1013.0f,
            123.45f,
            new Wind(2.57f, 125.1f),
            new Location(19.457216f, 51.759445f, 278.0f),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC"))
    );

    @Test
    void delete_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> measurementService.removeById(fakeMeasurement.getDeviceId(), fakeMeasurement.getTimestamp()));
    }

    @Test
    void save_method_should_return_saved_measurement_when_measurement_does_not_exist() {
        Mockito.when(measurementRepository.save(
                fakeMeasurement
        )).thenReturn(fakeMeasurement);

        //when
        Measurement savedMeasurement = measurementService.save(fakeMeasurement);

        //then
        Assertions.assertNotNull(savedMeasurement);
        Assertions.assertEquals(fakeMeasurement.getDeviceId(), savedMeasurement.getDeviceId());
        Assertions.assertEquals(fakeMeasurement.getTemperature(), savedMeasurement.getTemperature());
        Assertions.assertEquals(fakeMeasurement.getPressure(), savedMeasurement.getPressure());
        Assertions.assertEquals(fakeMeasurement.getHumidity(), savedMeasurement.getHumidity());
        Assertions.assertEquals(fakeMeasurement.getWind(), savedMeasurement.getWind());
        Assertions.assertEquals(fakeMeasurement.getLocation(), savedMeasurement.getLocation());
        Assertions.assertEquals(fakeMeasurement.getTimestamp().toLocalDateTime(), savedMeasurement.getTimestamp().toLocalDateTime());

    }

    @Test
    void save_method_should_throw_measurement_already_exist_exception_when_measurement_exist() {
        Mockito.when(measurementRepository.save(
                fakeMeasurement
        )).thenThrow(new MeasurementAlreadyExistsException());
        //when
        //then
        Assertions.assertThrows(MeasurementAlreadyExistsException.class,
                ()-> measurementService.save(fakeMeasurement));
    }


    @Test
    void find_by_id_method_should_return_founded_measurement_when_measurement_exist() {
        Mockito.when(measurementRepository.findById(fakeMeasurement.getDeviceId(), fakeMeasurement.getTimestamp())).thenReturn(Optional.of(fakeMeasurement));

        //when
        Measurement foundedMeasurement = measurementService.findById(fakeMeasurement.getDeviceId(), fakeMeasurement.getTimestamp());

        //then
        Assertions.assertNotNull(foundedMeasurement);
        Assertions.assertEquals(fakeMeasurement.getDeviceId(), foundedMeasurement.getDeviceId());
        Assertions.assertEquals(fakeMeasurement.getTemperature(), foundedMeasurement.getTemperature());
        Assertions.assertEquals(fakeMeasurement.getPressure(), foundedMeasurement.getPressure());
        Assertions.assertEquals(fakeMeasurement.getHumidity(), foundedMeasurement.getHumidity());
        Assertions.assertEquals(fakeMeasurement.getWind(), foundedMeasurement.getWind());
        Assertions.assertEquals(fakeMeasurement.getLocation(), foundedMeasurement.getLocation());
        Assertions.assertEquals(fakeMeasurement.getTimestamp().toLocalDateTime(), foundedMeasurement.getTimestamp().toLocalDateTime());
    }

    @Test
    void find_by_id_method_should_throw_measurement_not_found_exception_when_measurement_does_not_exist() {
        Mockito.when(measurementRepository.findById(fakeMeasurement.getDeviceId(), fakeMeasurement.getTimestamp())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(MeasurementNotFoundException.class,
                ()-> measurementService.findById(fakeMeasurement.getDeviceId(), fakeMeasurement.getTimestamp()));
    }

}