package ru.meteo.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.meteo.api.dto.SensorWithoutMeasurementsDTO;
import ru.meteo.api.models.Sensor;
import ru.meteo.api.services.SensorService;
import ru.meteo.api.util.SensorErrorResponse;
import ru.meteo.api.util.SensorNotSavedException;
import ru.meteo.api.util.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ru.meteo.api.services.SensorService sensorService,
                            SensorValidator sensorValidator) {

        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(
            @RequestBody @Valid SensorWithoutMeasurementsDTO sensorWithoutMeasurementsDTO,
            BindingResult bindingResult){

        Sensor sensor = SensorWithoutMeasurementsDTO.toSensor(sensorWithoutMeasurementsDTO);

        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()){
            List<FieldError> errorList = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : errorList){
                errorMessage
                        .append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotSavedException(errorMessage.toString());
        }

        sensorService.register(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private static ResponseEntity<SensorErrorResponse> handlerException(SensorNotSavedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
