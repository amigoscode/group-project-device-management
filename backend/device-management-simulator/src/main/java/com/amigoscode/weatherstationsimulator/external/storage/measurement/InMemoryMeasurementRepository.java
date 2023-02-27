package com.amigoscode.weatherstationsimulator.external.storage.measurement;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryMeasurementRepository {

    private List<MeasurementDao> list = new ArrayList<>();

    MeasurementDao insert(MeasurementDao measurementDao) {
        final int index = list.size();
        measurementDao.setId(index);
        list.add(measurementDao);

        return measurementDao;
    }

    Optional<MeasurementDao> findById(int id) {
        if (id >= list.size() || id <0) {
            return Optional.empty();
        }
        return Optional.of(list.get(id));
    }

    void deleteById(int id) {
        if (id >= list.size() || id < 0) {
            return;
        }

        list.remove(id);
        for (Integer i = 0; i < list.size(); i++) {
            list.get(i).setId(i);
        }
    }

    List<MeasurementDao> findAll() {
        return list.stream().toList();
    }

    int getSize() {
        return list.size();
    }

}
