package ru.meteo.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.meteo.api.dto.MeasurementDTO;
import ru.meteo.api.models.Measurement;
import ru.meteo.api.services.MeasurementService;
import ru.meteo.api.util.MeasurementErrorResponse;
import ru.meteo.api.util.MeasurementNotSavedException;
import ru.meteo.api.util.MeasurementValidator;
import ru.meteo.api.util.MeasurementsResponse;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public MeasurementsResponse getAllMeasurements(){
        return new MeasurementsResponse(
                measurementService.findAll().stream().map(MeasurementDTO::fromMeasurement).toList());
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount(){
        return measurementService.getRainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult){
        Measurement measurement = MeasurementDTO.toMeasurement(measurementDTO);

        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()){
            List<FieldError> errorList = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : errorList) {
                errorMessage
                        .append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotSavedException(errorMessage.toString());
        }

        measurementService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementNotSavedException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
