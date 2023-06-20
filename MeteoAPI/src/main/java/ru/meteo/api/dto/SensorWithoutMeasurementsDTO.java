package ru.meteo.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.meteo.api.models.Sensor;

public class SensorWithoutMeasurementsDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 30, message = "Имя должно быть длиной не менее 3 и не больше 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SensorWithoutMeasurementsDTO fromSensor(Sensor sensor){
        SensorWithoutMeasurementsDTO sensorDTO = new SensorWithoutMeasurementsDTO();
        sensorDTO.setName(sensor.getName());

        return sensorDTO;
    }

    public static Sensor toSensor(SensorWithoutMeasurementsDTO sensorDTO){
        return new Sensor(sensorDTO.getName());
    }
}
