package ru.meteo.api.util;

public class SensorErrorResponse {
    private String message;
    private long thrownAt;

    public SensorErrorResponse(String message, long throwAt) {
        this.message = message;
        this.thrownAt = throwAt;
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
