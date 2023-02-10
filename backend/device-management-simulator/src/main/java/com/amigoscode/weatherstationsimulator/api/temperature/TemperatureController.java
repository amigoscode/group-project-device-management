package com.amigoscode.weatherstationsimulator.api.temperature;

import com.amigoscode.weatherstationsimulator.api.temperature.dto.PageTemperatureDto;
import com.amigoscode.weatherstationsimulator.api.temperature.dto.TemperatureDto;
import com.amigoscode.weatherstationsimulator.api.temperature.mapper.PageTemperatureDtoMapper;
import com.amigoscode.weatherstationsimulator.api.temperature.mapper.TemperatureDtoMapper;
import com.amigoscode.weatherstationsimulator.domain.temperature.TemperatureService;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/temperatures")
public class TemperatureController {

    private final TemperatureService temperatureService;
    private final TemperatureDtoMapper temperatureDtoMapper;
    private final PageTemperatureDtoMapper pageTemperatureDtoMapper;

    @GetMapping
    public ResponseEntity<PageTemperatureDto> getTemperatures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        PageTemperatureDto pageTemperatures;

        pageTemperatures = pageTemperatureDtoMapper.toPageDto(temperatureService.findAll(pageable));

        return ResponseEntity.ok(pageTemperatures);
    }

    @GetMapping(path = "/{temperatureId}")
    public ResponseEntity<TemperatureDto> geTemperature(@PathVariable Long temperatureId) {
        Temperature temperature = temperatureService.findById(temperatureId);
        return ResponseEntity
                .ok(temperatureDtoMapper.toDto(temperature));
    }

}
