package com.amigoscode.weatherstationsimulator.external.measurement;

import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementRepository;
import com.amigoscode.weatherstationsimulator.domain.measurement.TakeMeasurement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class FakeTakeMeasurementAdapter implements TakeMeasurement {

    MeasurementRepository measurementRepository;

    DeviceService deviceService;

    @Override
    public Optional<Measurement> getResult() {
        if (measurementRepository.getSize() == 0)
            return Optional.empty();

        int index = ThreadLocalRandom.current().nextInt(0, measurementRepository.getSize());
        return Optional.of(measurementRepository.findAll().get(index));
    }

}
