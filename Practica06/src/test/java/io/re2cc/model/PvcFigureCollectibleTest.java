package io.re2cc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PvcFigureCollectibleTest {

    @Test
    void setUvbThresholdOverLimit() {
        PvcFigureCollectible collectible = new PvcFigureCollectible("Action Figure");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvbThreshold(31.0f));
    }

    @Test
    void setUvaThresholdOverLimit() {
        PvcFigureCollectible collectible = new PvcFigureCollectible("Action Figure");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvaThreshold(71.0f));
    }

    @Test
    void setTemperatureThresholdOverLimit() {
        PvcFigureCollectible collectible = new PvcFigureCollectible("Action Figure");
        assertThrows(IllegalArgumentException.class, () -> collectible.setTemperatureThreshold(36.0f));
    }

    @Test
    void setTemperatureThresholdValid() {
        PvcFigureCollectible collectible = new PvcFigureCollectible("Action Figure");
        assertDoesNotThrow(() -> collectible.setTemperatureThreshold(30.0f));
    }
}