package io.re2cc.model;

import java.io.IOException;

public class EnvironmentalSensor implements AutoCloseable {
    private boolean open = true;
    private String sensorId;

    public EnvironmentalSensor(String sensorId) {
        this.sensorId = sensorId;
        IO.println("[Sensor " + sensorId + "] Connection established.");
    }

    public float readTemperature() throws IOException {
        if (!open) {
            throw new IOException("Error: Sensor is closed.");
        }
        return 22.5f;
    }

    public float readHumidity() throws IOException {
        if (!open) {
            throw new IOException("Error: Sensor is closed.");
        }
        return 45.0f;
    }

    @Override
    public void close() throws Exception {
        open = false;
        IO.println("[Sensor " + sensorId + "] Connection closed successfully.");
    }
}
