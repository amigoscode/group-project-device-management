package com.amigoscode.weatherstationsimulator.domain.measurement;

import java.util.Optional;

public interface TakeMeasurement {
    Optional<Measurement> getResult();
}
