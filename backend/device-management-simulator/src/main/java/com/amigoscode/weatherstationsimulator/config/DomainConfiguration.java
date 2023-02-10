package com.amigoscode.weatherstationsimulator.config;

import com.amigoscode.weatherstationsimulator.domain.temperature.TemperatureMeasurement;
import com.amigoscode.weatherstationsimulator.domain.temperature.TemperaturePublishing;
import com.amigoscode.weatherstationsimulator.domain.temperature.TemperatureRepository;
import com.amigoscode.weatherstationsimulator.domain.temperature.TemperatureService;
import com.amigoscode.weatherstationsimulator.external.measurement.temperature.FakeTemperatureMeasurementAdapter;
import com.amigoscode.weatherstationsimulator.external.storage.temperature.JpaTemperatureRepository;
import com.amigoscode.weatherstationsimulator.external.storage.temperature.TemperatureEntityMapper;
import com.amigoscode.weatherstationsimulator.external.storage.temperature.TemperatureStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("domain.properties")
class DomainConfiguration {

    @Bean
    public TemperatureMeasurement temperatureMeasurement() {
        return new FakeTemperatureMeasurementAdapter();
    }

    @Bean
    public TemperatureRepository temperatureRepository(JpaTemperatureRepository jpaTemperatureRepository, TemperatureEntityMapper temperatureEntityMapper) {
        return new TemperatureStorageAdapter(jpaTemperatureRepository, temperatureEntityMapper);
    }

    @Bean
    public TemperatureService temperatureService(TemperatureRepository temperatureRepository, TemperatureMeasurement temperatureMeasurement, TemperaturePublishing temperaturePublishing) {
        return new TemperatureService(temperatureRepository, temperatureMeasurement, temperaturePublishing);
    }
}
