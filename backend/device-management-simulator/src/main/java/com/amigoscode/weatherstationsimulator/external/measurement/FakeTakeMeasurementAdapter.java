package com.amigoscode.weatherstationsimulator.external.measurement;

import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementNotFoundException;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementRepository;
import com.amigoscode.weatherstationsimulator.domain.measurement.TakeMeasurement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class FakeTakeMeasurementAdapter implements TakeMeasurement {

    MeasurementRepository measurementRepository;

    DeviceService deviceService;

    @Override
    public Measurement getResult() {
        int index = ThreadLocalRandom.current().nextInt(0, measurementRepository.getSize());
        final Measurement measurement = measurementRepository.findById(index).orElseThrow(() -> new MeasurementNotFoundException());
        measurement.setDeviceId(deviceService.getDevice().getDeviceId());
        measurement.setTimestamp(ZonedDateTime.now());
        return measurement;
    }

}
