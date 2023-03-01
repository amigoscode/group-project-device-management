package com.amigoscode.devicemanagement.domain.measurement;

import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.PageMeasurement;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MeasurementRepository {

    Measurement save(Measurement measurement);

    void remove(String id);

    Optional<Measurement> findById(String id);

    PageMeasurement findAll(Pageable pageable);

    PageMeasurement findAllByDeviceId(Pageable pageable, String deviceId);

}
