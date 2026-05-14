package io.re2cc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalCollectibleTest {

    @Test
    void setHumidityThresholdOver100() {
        PhysicalCollectible collectible = new PhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(101.0f));
    }

    @Test
    void setHumidityThresholdUnder100() {
        PhysicalCollectible collectible = new PhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(-1.0f));
    }

    @Test
    void setHumidityThresholdValid() {
        PhysicalCollectible collectible = new PhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertDoesNotThrow(() -> collectible.setHumidityThreshold(50.0f));
    }

    @Test
    void setTemperatureThresholdUnderPossible() {
        PhysicalCollectible collectible = new PhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> collectible.setTemperatureThreshold(-300.0f));
    }

    @Test
    void setTemperatureThresholdPossible() {
        PhysicalCollectible collectible = new PhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertDoesNotThrow(() -> collectible.setTemperatureThreshold(-200.0f));
    }
}