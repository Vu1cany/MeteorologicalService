package ru.meteo.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.meteo.api.models.Sensor;

@Component
public class SensorValidator implements Validator {
    private final ru.meteo.api.services.SensorService sensorService;

    @Autowired
    public SensorValidator(ru.meteo.api.services.SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue(
                    "name",
                    "",
                    "Сенсор с таким названием уже добавлен");
        }
    }
}
