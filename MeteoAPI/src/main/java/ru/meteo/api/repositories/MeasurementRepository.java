package ru.meteo.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meteo.api.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
