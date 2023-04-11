package com.amigoscode.weatherstationsimulator.domain.measurement;

import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MeasurementService {

    private final DeviceService deviceService;

    private final MeasurementRepository measurementRepository;

    private final TakeMeasurement takeMeasurement;


    private final MeasurementPublishing measurementPublishing;

    public Long save(Measurement measurement){
        return measurementRepository.save(measurement);
    }

    public void removeById(Long id){
        measurementRepository.remove(id);
    }

    public Measurement findById(Long id){
        return measurementRepository.findById(id)
                .orElseThrow(MeasurementNotFoundException::new);
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    public Optional<Measurement> takeAndPublishMeasurement() {
        final Optional<Measurement> result = takeMeasurement.getResult();
        if(result.isEmpty())
            return result;

        result.get().setDeviceId(deviceService.getDevice().getId());
        result.get().setTimestamp(ZonedDateTime.now());
        measurementPublishing.publish(result.get());
        return result;
    }

}
