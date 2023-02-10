package com.amigoscode.weatherstationsimulator.external.storage.temperature;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTemperatureRepository extends JpaRepository<TemperatureEntity, Long> {
}
