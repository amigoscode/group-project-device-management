package com.amigoscode.weatherstationsimulator.external.storage.temperature;

import com.amigoscode.weatherstationsimulator.domain.temperature.TemperatureRepository;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.PageTemperature;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class TemperatureStorageAdapter implements TemperatureRepository {

    private final JpaTemperatureRepository temperatureRepository;
    private final TemperatureEntityMapper mapper;

    @Override
    public Temperature save(final Temperature temperature) {
        return mapper.toDomain(temperatureRepository.save(mapper.toEntity(temperature)));
    }

    @Override
    public void update(final Temperature temperature) {
        temperatureRepository
                .findById(temperature.getId())
                .ifPresent(quizEntity -> temperatureRepository.save(mapper.toEntity(temperature)));
    }

    @Override
    public void remove(final Long id) {
        temperatureRepository.deleteById(id);
    }

    @Override
    public Optional<Temperature> findById(final Long id) {
        return temperatureRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageTemperature findAll(final Pageable pageable) {
        Page<TemperatureEntity> pageOfTemperatureEntity = temperatureRepository.findAll(pageable);
        List<Temperature> temperaturesOnCurrentPage = pageOfTemperatureEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageTemperature(
                temperaturesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfTemperatureEntity.getTotalPages(),
                pageOfTemperatureEntity.getTotalElements()
        );
    }
}
