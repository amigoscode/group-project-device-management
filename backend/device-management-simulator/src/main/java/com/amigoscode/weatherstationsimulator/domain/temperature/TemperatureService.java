package com.amigoscode.weatherstationsimulator.domain.temperature;

import com.amigoscode.weatherstationsimulator.domain.temperature.exceptions.TemperatureNotFoundException;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.PageTemperature;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final TemperatureMeasurement temperatureMeasurement;
    private final TemperaturePublishing temperaturePublishing;

    public Temperature save(Temperature temperature) {
        return temperatureRepository.save(temperature);
    }

    public void update(Temperature temperature) {
        temperatureRepository.update(temperature);
    }

    public void removeById(Long id) {
        temperatureRepository.remove(id);
    }

    public Temperature findById(Long id) {
        return temperatureRepository.findById(id)
                .orElseThrow(TemperatureNotFoundException::new);
    }

    public PageTemperature findAll(Pageable pageable) {
        return temperatureRepository.findAll(pageable);
    }

    public Temperature measureTemperatureAndRecordResult() {
        var actualTemperature = temperatureMeasurement.measureValue();
        var savedTemperature = temperatureRepository.save(actualTemperature);
        temperaturePublishing.publish(savedTemperature);
        return savedTemperature;
    }
}
