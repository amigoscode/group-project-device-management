package com.amigoscode.devicemanagement.domain.measurement;

import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.PageMeasurement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    public Measurement save(Measurement measurement){
        return measurementRepository.save(measurement);
    }

    public void removeById(String id){
        measurementRepository.remove(id);
    }

    public Measurement findById(String id){
        return measurementRepository.findById(id)
                .orElseThrow(MeasurementNotFoundException::new);
    }

    public PageMeasurement findAll(Pageable pageable){
        return measurementRepository.findAll(pageable);
    }

    public PageMeasurement findAllByDeviceId(final Pageable pageable, final String deviceId) {
        return measurementRepository.findAllByDeviceId(pageable, deviceId);
    }
}
