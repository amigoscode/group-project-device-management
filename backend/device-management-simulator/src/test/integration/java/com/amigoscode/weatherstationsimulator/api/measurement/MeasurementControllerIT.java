package com.amigoscode.weatherstationsimulator.api.measurement;


import com.amigoscode.weatherstationsimulator.BaseIT;
import com.amigoscode.weatherstationsimulator.TestMeasurementFactory;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementRepository;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MeasurementControllerIT extends BaseIT {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private MeasurementDtoMapper measurementDtoMapper;


    @Test
    void user_should_be_able_to_get_information_about_measurement() {
        //given
        Measurement measurement = TestMeasurementFactory.createRandom();
        Long id = measurementService.save(measurement);


        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/measurements/" + id,
                null,
                MeasurementDto.class);

        //then
        MeasurementDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareMeasurements(measurement, measurementDtoMapper.toDomain(body));
    }

    @Test
    void user_should_be_able_to_get_information_about_all_measurements() {
        //given
        measurementService.save(TestMeasurementFactory.createRandom());
        measurementService.save(TestMeasurementFactory.createRandom());
        measurementService.save(TestMeasurementFactory.createRandom());
        measurementService.save(TestMeasurementFactory.createRandom());
        measurementService.save(TestMeasurementFactory.createRandom());
        measurementService.save(TestMeasurementFactory.createRandom());
        measurementService.save(TestMeasurementFactory.createRandom());


        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/measurements",
                null,
                List.class);

        //then
        List<MeasurementDto> body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(7, body.size());
    }

    @Test
    void user_should_be_able_to_create_measurement() {
        //given
        Measurement measurement = TestMeasurementFactory.createRandom();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/measurements",
                measurementDtoMapper.toDto(measurement),
                Long.class);

        //then
        Long body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertNotNull(body);
    }

    @Test
    void user_should_be_able_to_delete_measurement() {
        //given
        Measurement measurement = TestMeasurementFactory.createRandom();
        measurementRepository.save(measurement);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/measurements/" + 0,
                null,
                MeasurementDto.class);

        //then
        MeasurementDto body = response.getBody();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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
