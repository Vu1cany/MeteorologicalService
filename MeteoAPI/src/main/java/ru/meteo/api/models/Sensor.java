package ru.meteo.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sensors")
public class Sensor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 30, message = "Имя должно быть длиной не менее 3 и не больше 30 символов")
    private String name;

    @OneToMany(mappedBy = "sensor")
    //@JsonManagedReference
    private List<Measurement> measurements;

    public Sensor(String name) {
        this.name = name;
    }

    public Sensor() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor sensor)) return false;
        return Objects.equals(id, sensor.id) && Objects.equals(name, sensor.name) && Objects.equals(measurements, sensor.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, measurements);
    }
}
