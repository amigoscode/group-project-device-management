package com.amigoscode.devicemanagement.external.storage.measurement;

import com.amigoscode.devicemanagement.domain.measurement.MeasurementRepository;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.PageMeasurement;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
@Component
class MeasurementStorageAdapter implements MeasurementRepository {

    private final DynamoMeasurementRepository measurementRepository;

    private final MeasurementEntityMapper mapper;

    @Override
    public Measurement save(Measurement measurement){
        try{
            MeasurementEntity saved = measurementRepository.save(mapper.toEntity(measurement));
            log.info("Saved entity" + saved);
            return mapper.toDomain(saved);
        } catch (DuplicateKeyException ex) {
            log.warning("Measurement created at " + measurement.getTimestamp() + "for device " +measurement.getDeviceId() + " already exists in db");
            throw new MeasurementAlreadyExistsException();

        }
    }

    @Override
    public void remove(String id) {
        measurementRepository.findById(id).ifPresent(deviceEntity -> measurementRepository.deleteById(id));
    }

    @Override
    public Optional<Measurement> findById(String id) {
        return measurementRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageMeasurement findAll(Pageable pageable) {
        Page<MeasurementEntity> pageOfMeasurementsEntity = measurementRepository.findAll(pageable);
        List<Measurement> measurementsOnCurrentPage = pageOfMeasurementsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageMeasurement(
                measurementsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfMeasurementsEntity.getTotalPages(),
                pageOfMeasurementsEntity.getTotalElements()
        );
    }

    @Override
    public PageMeasurement findAllByDeviceId(final Pageable pageable, final String deviceId) {
        Page<MeasurementEntity> pageOfMeasurementsEntity = measurementRepository.findAllByDeviceId(pageable, deviceId);
        List<Measurement> measurementsOnCurrentPage = pageOfMeasurementsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageMeasurement(
                measurementsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfMeasurementsEntity.getTotalPages(),
                pageOfMeasurementsEntity.getTotalElements()
        );
    }

}
