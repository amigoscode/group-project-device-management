package com.amigoscode.devicemanagement.api.measurement;


import com.amigoscode.devicemanagement.api.verifier.AuthVerifyDevice;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementNotFoundException;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementService;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/devices",
        produces = "application/json",
        consumes = "application/json"
)

class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementDtoMapper measurementMapper;
    private final PageMeasurementDtoMapper pageMeasurementDtoMapper;

    @GetMapping( path = "{deviceId}/measurements/{timestamp}")
    @AuthVerifyDevice
    public ResponseEntity<MeasurementDto> getMeasurement(@PathVariable String deviceId, @PathVariable ZonedDateTime timestamp) {
        Measurement measurement = measurementService.findById(deviceId, timestamp);

        return ResponseEntity
                .ok(measurementMapper.toDto(measurement));
    }

    @GetMapping( path = "{deviceId}/measurements")
    @AuthVerifyDevice
    public ResponseEntity<PageMeasurementDto> getMeasurements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @PathVariable String deviceId
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageMeasurementDto pageMeasurements = pageMeasurementDtoMapper.toPageDto(measurementService.findAllByDeviceId(pageable, deviceId));


        return ResponseEntity.ok(pageMeasurements);
    }

}
