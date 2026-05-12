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
                IO.println("Advertencia, el material " + material + " no esta registrado. Se usaran valores predeterminados");
                break;
        }
    }

}
