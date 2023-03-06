package com.amigoscode.weatherstationsimulator.external.schedule;

import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SimpleScheduler {

    private final MeasurementService measurementService;
    private static final Logger log = LoggerFactory.getLogger(SimpleScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void takeAndPublishMeasurement() {
        final Optional<Measurement> measurement = measurementService.takeAndPublishMeasurement();
        log.info("The current measurement is {}", measurement);
    }
}
