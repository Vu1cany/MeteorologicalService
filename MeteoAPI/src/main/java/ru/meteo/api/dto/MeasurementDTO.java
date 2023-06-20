package ru.meteo.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.meteo.api.models.Measurement;

public class MeasurementDTO {
    @NotNull(message = "Значение температуры не должно быть пустым")
    @Max(value = 100, message = "Значение температуры должно быть не более 100")
    @Min(value = -100, message = "Значение температуры должно быть не менее -100")
    private Double value;

    @NotNull(message = "Поле 'raining' не должно быть пустым")
    private Boolean raining;

    @NotNull(message = "Поле 'sensor' не должно быть пустым")
    private SensorWithoutMeasurementsDTO sensor;

    public MeasurementDTO(Double value, Boolean raining, SensorWithoutMeasurementsDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public MeasurementDTO() {}

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

    public SensorWithoutMeasurementsDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorWithoutMeasurementsDTO sensor) {
        this.sensor = sensor;
    }

    public static MeasurementDTO fromMeasurement(Measurement measurement){
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(measurement.getValue());
        measurementDTO.setRaining(measurement.getRaining());
        measurementDTO.setSensor(SensorWithoutMeasurementsDTO.fromSensor(measurement.getSensor()));

        return measurementDTO;
    }

    public static Measurement toMeasurement(MeasurementDTO measurementDTO){
        return new Measurement(
                measurementDTO.value,
                measurementDTO.raining,
                null,
                SensorWithoutMeasurementsDTO.toSensor(measurementDTO.sensor)
        );
    }
}
