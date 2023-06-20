package ru.meteo.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    @NotNull(message = "Значение температуры не должно быть пустым")
    @Max(value = 100, message = "Значение температуры должно быть не более 100")
    @Min(value = -100, message = "Значение температуры должно быть не менее -100")
    private Double value;

    @Column(name = "is_raining")
    @NotNull(message = "Поле 'raining' не должно быть пустым")
    private Boolean raining;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    @NotNull(message = "Поле 'sensor' не должно быть пустым")
    //@JsonBackReference
    private Sensor sensor;

    public Measurement(Double value, Boolean raining, LocalDateTime addedAt, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.addedAt = addedAt;
        this.sensor = sensor;
    }

    public Measurement() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value) && Objects.equals(raining, that.raining) && Objects.equals(addedAt, that.addedAt) && Objects.equals(sensor, that.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, raining, addedAt, sensor);
    }
}
