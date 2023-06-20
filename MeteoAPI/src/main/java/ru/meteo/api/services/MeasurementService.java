package ru.meteo.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meteo.api.models.Measurement;
import ru.meteo.api.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    @Transactional
    public void save(Measurement measurement){
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    public long getRainyDaysCount(){
        return measurementRepository.findAll().stream().filter(Measurement::getRaining).count();
    }

    private void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setAddedAt(LocalDateTime.now());
    }
}
