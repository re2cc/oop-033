package io.re2cc.model;

public class OilPaintingCollectible extends PhysicalCollectible {
    public OilPaintingCollectible(String name) {
        super(name, 0.3f, 0.3f, 0.3f, 0.3f);
    }

    @Override
    public void setUvbThreshold(float uvbThreshold) {
        if (uvbThreshold < 0) {
            throw new IllegalArgumentException("Error: Impossible UVB. The value must be greater than 0.");
        }

        if (uvbThreshold > 50) {
            throw new IllegalArgumentException("Error: Oil paintings are very sensitive to UVB decoloration. The value" +
                    " must be lesser than 50");
        }

        super.setUvbThreshold(uvbThreshold);
    }

    @Override
    public void setUvaThreshold(float uvaThreshold) {
        if (uvaThreshold < 0) {
            throw new IllegalArgumentException("Error: Impossible UVA. The value must be greater than 0.");
        }

        if (uvaThreshold > 50) {
            throw new IllegalArgumentException("Error: Oil paintings are very sensitive to UVA decoloration. The value" +
                    " must be lesser than 50");
        }

        super.setUvaThreshold(uvaThreshold);
    }

    @Override
    public String toString() {
        return String.format("OilPaintCollectible[Name: %s | UVA): %.2f | UVB): %.2f | Temperature: %.2f° C | Humidity: %.2f%%]",
                getName(), getUvaThreshold(), getUvbThreshold(), getTemperatureThreshold(), getHumidityThreshold());
    }
}
