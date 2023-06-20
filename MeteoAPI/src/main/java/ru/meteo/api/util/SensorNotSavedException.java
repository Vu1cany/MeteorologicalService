package ru.meteo.api.util;

public class SensorNotSavedException extends RuntimeException{
    public SensorNotSavedException(String message) {
        super(message);
    }
}
