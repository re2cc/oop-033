package io.re2cc.model;

import java.util.Comparator;

public abstract class PhysicalCollectible extends Collectible implements Preservable {
    private static final long serialVersionUID = 1L;

    public static final Comparator<PhysicalCollectible> BY_TEMPERATURE =
            Comparator.comparingDouble(PhysicalCollectible::getTemperatureThreshold);

    public static final Comparator<PhysicalCollectible> BY_HUMIDITY =
            Comparator.comparingDouble(PhysicalCollectible::getHumidityThreshold);

    protected float uvaThreshold;
    protected float uvbThreshold;
    protected float temperatureThreshold;
    protected float humidityThreshold;

    public PhysicalCollectible(String name, float uvaThreshold, float uvbThreshold, float temperatureThreshold,
            float humidityThreshold) {
        super(name);
        setUvaThreshold(uvaThreshold);
        setUvbThreshold(uvbThreshold);
        setTemperatureThreshold(temperatureThreshold);
        setHumidityThreshold(humidityThreshold);
    }

    public float getHumidityThreshold() {
        return humidityThreshold;
    }

    public void setHumidityThreshold(float humidityThreshold) {
        if (humidityThreshold < 0 || humidityThreshold > 100) { // Is percentage
            throw new IllegalArgumentException("Error: Humidity must be between 0% y 100%.");
        }
        this.humidityThreshold = humidityThreshold;
    }

    public float getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public void setTemperatureThreshold(float temperatureThreshold) {
        if (temperatureThreshold < -273.15f) { // Less than that is less than 0 K which is impossible
            throw new IllegalArgumentException(
                    "Error: Impossible temperature. The value must be greater than -273.15° C.");
        }
        this.temperatureThreshold = temperatureThreshold;
    }

    public float getUvaThreshold() {
        return uvaThreshold;
    }

    public void setUvaThreshold(float uvaThreshold) {
        this.uvaThreshold = uvaThreshold;
    }

    public float getUvbThreshold() {
        return uvbThreshold;
    }

    public void setUvbThreshold(float uvbThreshold) {
        this.uvbThreshold = uvbThreshold;
    }

    @Override
    public String toString() {
        return String.format(
                "PhysicalCollectible[Name: %s | UVA: %.2f | UVB: %.2f | Temperature: %.2f° C | Humidity: %.2f%%]",
                getName(), uvaThreshold, uvbThreshold, temperatureThreshold, humidityThreshold);
    }

    public void elevateThreshold(float value) {
        setUvaThreshold(this.uvaThreshold + value);
        setUvbThreshold(this.uvbThreshold + value);
        setTemperatureThreshold(this.temperatureThreshold + value);
        setHumidityThreshold(this.humidityThreshold + value);
    }

    public float uvIndexAproxThreshold() {
        // Example equation, UV index calculation is more complex than this
        return this.uvaThreshold + (this.uvbThreshold * 2.5f);
    }

    public boolean hasExceedTemperatureThreshold(float currentTemperature) {
        return currentTemperature > this.temperatureThreshold;
    }

    public float getSuggestedTemperature() {
        return this.temperatureThreshold - 5;
    }

    public abstract float getBaseDepreciationRate();

    public abstract float getRiskMultiplier();

    public float calculateMaintenanceCost(float baseValue) {
        return baseValue * getRiskMultiplier() + (baseValue * getBaseDepreciationRate());
    }
}
