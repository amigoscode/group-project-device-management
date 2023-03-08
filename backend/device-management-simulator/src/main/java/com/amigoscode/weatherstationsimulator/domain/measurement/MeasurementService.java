package com.amigoscode.weatherstationsimulator.domain.measurement;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MeasurementService {

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

        measurementPublishing.publish(result.get());
        return result;
    }

}
