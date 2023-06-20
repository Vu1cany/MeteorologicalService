package ru.meteo.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.meteo.api.models.Measurement;
import ru.meteo.api.services.SensorService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (sensorService.findByName(measurement.getSensor().getName()).isEmpty()){
            errors.rejectValue(
                    "sensor",
                    "",
                    "Такой сенсор не зарегистрирован");
        }
    }
}
