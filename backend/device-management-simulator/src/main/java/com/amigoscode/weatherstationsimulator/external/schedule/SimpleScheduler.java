package com.amigoscode.weatherstationsimulator.external.schedule;

import com.amigoscode.weatherstationsimulator.domain.temperature.TemperatureService;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SimpleScheduler {

    private final TemperatureService temperatureService;
    private static final Logger log = LoggerFactory.getLogger(SimpleScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        final Temperature temperature = temperatureService.measureTemperatureAndRecordResult();
//        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("The current temperature is {}", temperature);
    }
}
