package com.amigoscode.weatherstationsimulator.external.storage.measurement;


import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log
@Component
public
class MeasurementStorageAdapter implements MeasurementRepository {

    private final InMemoryMeasurementRepository measurementRepository;

    private final MeasurementDaoMapper mapper;

    @Override
    public Measurement save(Measurement measurement){
        MeasurementDao saved = measurementRepository.insert(mapper.toEntity(measurement));
        log.info("Saved entity" + saved);
        return mapper.toDomain(saved);
    }

    @Override
    public void remove(final Integer id) {
        measurementRepository.findById(id).ifPresent(measurementDao -> measurementRepository.deleteById(id));
    }

    @Override
    public Optional<Measurement> findById(final Integer id) {
        return measurementRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Measurement> findAll() {
        return measurementRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public int getSize() {
        return measurementRepository.getSize();
    }

}
