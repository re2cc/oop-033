package io.re2cc.practica02;

public class Collectible {
    String name;
    float uvaThreshold;
    float uvbThreshold;
    float temperatureThreshold;
    float humidityThreshold;

    Collectible(String name, float uvaThreshold,  float uvbThreshold, float temperatureThreshold, float humidityThreshold) {
        this.name = name;
        this.uvaThreshold = uvaThreshold;
        this.uvbThreshold = uvbThreshold;
        this.temperatureThreshold = temperatureThreshold;
        this.humidityThreshold = humidityThreshold;
    }

    Collectible(String name) {
        this(name, 0.5f, 0.5f, 0.5f, 0.5f);
    }

    Collectible(String name, String material) {
        this(name);

        switch (material) {
            case "PVC":
                this.uvaThreshold = 0.1f;
                this.uvbThreshold = 0.1f;
                this.temperatureThreshold = 0.1f;
                this.humidityThreshold = 0.1f;
                break;
            case "Paper":
                this.uvaThreshold = 0.2f;
                this.uvbThreshold = 0.2f;
                this.temperatureThreshold = 0.2f;
                this.humidityThreshold = 0.2f;
                break;
            case "Oil":
                this.uvaThreshold = 0.3f;
                this.uvbThreshold = 0.3f;
                this.temperatureThreshold = 0.3f;
                this.humidityThreshold = 0.3f;
                break;
            default:
                IO.println("Warning: The material " + material + " is not registered. Default values will be used.");
                break;
        }
    }

    void printThreshold() {
        IO.println("Collectible: " + this.name);
        IO.println("UV A: " + this.uvaThreshold);
        IO.println("UV B: " + this.uvbThreshold);
        IO.println("Temperature: " + this.temperatureThreshold);
        IO.println("Humidity: " + this.humidityThreshold);
    }

    void elevateThreshold(float value) {
        this.uvaThreshold += value;
        this.uvbThreshold += value;
        this.temperatureThreshold += value;
        this.humidityThreshold += value;
    }

    float uvIndexAproxThreshold() {
        // Example equation, UV index calculation is more complex than this
        return this.uvaThreshold + (this.uvbThreshold * 2.5f);
    }

    boolean hasExceedTemperatureThreshold(float currentTemperature) {
        return currentTemperature > this.temperatureThreshold;
    }

    float getSuggestedTemperature() {
        return this.temperatureThreshold - 5;
    }
}
