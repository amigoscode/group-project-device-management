package com.amigoscode.weatherstationsimulator.domain.temperature;

import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;

public interface TemperaturePublishing {
    void publish(Temperature temperature);
}
