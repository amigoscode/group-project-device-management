package com.amigoscode.weatherstationsimulator.domain.measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {

    Measurement save(Measurement measurement);

    void remove(Integer id);

    Optional<Measurement> findById(Integer id);

    List<Measurement> findAll();

    int getSize();

}
