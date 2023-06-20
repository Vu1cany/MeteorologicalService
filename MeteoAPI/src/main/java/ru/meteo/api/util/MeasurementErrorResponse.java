package ru.meteo.api.util;

public class MeasurementErrorResponse {
    private String message;
    private long thrownAt;

    public MeasurementErrorResponse(String message, long thrownAt) {
        this.message = message;
        this.thrownAt = thrownAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getThrownAt() {
        return thrownAt;
    }

    public void setThrownAt(long thrownAt) {
        this.thrownAt = thrownAt;
    }
}
