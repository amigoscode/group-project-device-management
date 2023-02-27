package com.amigoscode.weatherstationsimulator.api.measurement;


import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/measurements",
        produces = "application/json",
        consumes = "application/json"
)

class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementDtoMapper measurementMapper;

    @PostMapping
    public ResponseEntity<MeasurementDto> saveMeasurement(@RequestBody MeasurementDto dto) {
        Measurement measurement = measurementService.save(measurementMapper.toDomain(dto));
        return ResponseEntity
                .ok(measurementMapper.toDto(measurement));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Integer id){
        measurementService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping( path = "{measurementId}")
    public ResponseEntity<MeasurementDto> getMeasurement(@PathVariable Integer measurementId) {
        Measurement measurement = measurementService.findById(measurementId);

        return ResponseEntity
                .ok(measurementMapper.toDto(measurement));
    }

    @GetMapping
    public ResponseEntity<List<MeasurementDto>> getMeasurements() {
        List<MeasurementDto> measurements = measurementService.findAll().stream().map(measurementMapper::toDto).toList();

        return ResponseEntity.ok(measurements);
    }

}
