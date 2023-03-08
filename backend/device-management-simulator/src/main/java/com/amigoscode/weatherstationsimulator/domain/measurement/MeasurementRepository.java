package com.amigoscode.weatherstationsimulator.domain.measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {

    Long save(Measurement measurement);

    void remove(Long id);

    void removeAll();

    Optional<Measurement> findById(Long id);

    List<Measurement> findAll();

    int getSize();

}
