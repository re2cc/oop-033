package io.re2cc.model;

public class PvcFigureCollectible extends PhysicalCollectible {
    public PvcFigureCollectible(String name) {
        super(name, 25.0f, 25.0f, 27.0f, 70.0f);
    }

    @Override
    public void setUvbThreshold(float uvbThreshold) {
        if (uvbThreshold < 0) {
            throw new IllegalArgumentException("Error: Impossible UVB. The value must be greater than 0.");
        }

        if (uvbThreshold > 30) {
            throw new IllegalArgumentException("Error: PVC are very sensitive to UVB decoloration. The value" +
                    " must be lesser than 30");
        }

        super.setUvbThreshold(uvbThreshold);
    }

    @Override
    public void setUvaThreshold(float uvaThreshold) {
        if (uvaThreshold < 0) {
            throw new IllegalArgumentException("Error: Impossible UVA. The value must be greater than 0.");
        }

        if (uvaThreshold > 70) {
            throw new IllegalArgumentException("Error: PVC are very sensitive to UVA caused brittleness. The value" +
                    " must be lesser than 70");
        }

        super.setUvaThreshold(uvaThreshold);
    }

    @Override
    public void setTemperatureThreshold(float temperatureThreshold) {
        if (temperatureThreshold > 35.0) {
            throw new IllegalArgumentException("Error: PVC are very susceptible to bending under high temperatures. The" +
                    " value must be lesser than 35.0° C");
        }

        super.setTemperatureThreshold(temperatureThreshold);
    }

    @Override
    public String toString() {
        return String.format("PvcFigureCollectible[Name: %s | UVA: %.2f | UVB: %.2f | Temperature: %.2f° C | Humidity: %.2f%%]",
                getName(), getUvaThreshold(), getUvbThreshold(), getTemperatureThreshold(), getHumidityThreshold());
    }
}
