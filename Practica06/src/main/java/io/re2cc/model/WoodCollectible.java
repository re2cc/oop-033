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

    @Override
    public boolean needsSpecialCare() {
        return true;
    }

    @Override
    public String getPreservationInstructions() {
        return "Keep in dry environments (humidity below 20%) and apply varnish regularly to prevent decay.";
    }

    @Override
    public float getBaseDepreciationRate() {
        return 0.08f;
    }

    @Override
    public float getRiskMultiplier() {
        return 1.5f;
    }

    @Override
    public double estimateValue(double basePrice) {
        return basePrice;
    }

    @Override
    public double estimateValue(double basePrice, int yearsElapsed) {
        return basePrice * Math.pow(0.95, yearsElapsed);
    }

    @Override
    public double estimateValue(double basePrice, int yearsElapsed, boolean isRare) {
        if (isRare) {
            return estimateValue(basePrice, yearsElapsed) * 1.8;
        }
        return estimateValue(basePrice, yearsElapsed);
    }

    public void applyVarnish() {
        IO.println("Applying protective varnish to Wood collectible: " + getName());
    }
}
