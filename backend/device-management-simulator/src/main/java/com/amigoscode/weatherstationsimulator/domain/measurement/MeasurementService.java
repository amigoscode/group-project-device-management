package com.amigoscode.weatherstationsimulator.domain.measurement;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final TakeMeasurement takeMeasurement;

    private final MeasurementPublishing measurementPublishing;

    public Measurement save(Measurement measurement){
        return measurementRepository.save(measurement);
    }

    public void removeById(Integer id){
        measurementRepository.remove(id);
    }

    public Measurement findById(Integer id){
        return measurementRepository.findById(id)
                .orElseThrow(MeasurementNotFoundException::new);
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    public Integer getSizeofRepository() {
        return measurementRepository.getSize();
    }

    public Measurement takeAndPublishMeasurement() {
        final Measurement result = takeMeasurement.getResult();
        measurementPublishing.publish(result);
        return result;
    }

}
