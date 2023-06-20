package ru.meteo.api.util;

public class MeasurementNotSavedException extends RuntimeException{
    public MeasurementNotSavedException(String message) {
        super(message);
    }
}
