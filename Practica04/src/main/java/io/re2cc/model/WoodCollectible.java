package io.re2cc.model;

public class WoodCollectible extends PhysicalCollectible {
    public WoodCollectible(String name) {
        super(name, 10.0f, 30.0f, 37.0f, 10.0f);
    }

    @Override
    public void setUvbThreshold(float uvbThreshold) {
        if (uvbThreshold < 5) {
            throw new IllegalArgumentException("Error: Wood requires a minimum of UVB. The value must be greater than 5.");
        }

        super.setUvbThreshold(uvbThreshold);
    }

    @Override
    public void setHumidityThreshold(float humidityThreshold) {
        if (humidityThreshold > 20) {
            throw new IllegalArgumentException("Error: Wood is very sensitive to humidity. The" +
                    " value must be lesser than 20%.");
        }

        super.setHumidityThreshold(humidityThreshold);
    }

    @Override
    public String toString() {
        return String.format("WoodCollectible[Name: %s | UVA: %.2f | UVB: %.2f | Temperature: %.2f° C | Humidity: %.2f%%]",
                getName(), getUvaThreshold(), getUvbThreshold(), getTemperatureThreshold(), getHumidityThreshold());
    }
}
