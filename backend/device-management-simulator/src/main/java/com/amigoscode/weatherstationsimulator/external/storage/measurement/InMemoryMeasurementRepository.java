package com.amigoscode.weatherstationsimulator.external.storage.measurement;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryMeasurementRepository {


    private Map<Long, MeasurementDao> map = new HashMap<>();

    private Long id = 0l;

    Long insert(MeasurementDao measurementDao) {
        id++;
        measurementDao.setId(id);
        map.put(id, measurementDao);

        return id;
    }

    Optional<MeasurementDao> findById(Long id) {
        if (map.containsKey(id)) {
            return Optional.of(map.get(id));
        }
        return Optional.empty();
    }

    void deleteById(Long id) {
        map.remove(id);
    }

    void drop() {
        id = 0l;
        map.clear();
    }

    List<MeasurementDao> findAll() {
        List<MeasurementDao> list = new ArrayList<MeasurementDao>(map.values());
        return list;
    }

    int getSize() {
        return map.size();
    }

}
