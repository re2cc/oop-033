package io.re2cc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WoodCollectibleTest {

    @Test
    void setUvbThresholdUnderMinimum() {
        WoodCollectible collectible = new WoodCollectible("Wooden Statue");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvbThreshold(4.0f));
    }

    @Test
    void setHumidityThresholdOverLimit() {
        WoodCollectible collectible = new WoodCollectible("Wooden Statue");
        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(21.0f));
    }

    @Test
    void setUvbThresholdValid() {
        WoodCollectible collectible = new WoodCollectible("Wooden Statue");
        assertDoesNotThrow(() -> collectible.setUvbThreshold(10.0f));
    }
}