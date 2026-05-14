package io.re2cc.model;

import io.re2cc.model.Collectible;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectibleTest {

    @Test
    void setHumidityThresholdOver100() {
        Collectible collectible = new Collectible("Item");

        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(101.0f));
    }

    @Test
    void setHumidityThresholdUnder100() {
        Collectible collectible = new Collectible("Item");

        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(-1.0f));
    }

    @Test
    void setHumidityThresholdValid() {
        Collectible collectible = new Collectible("Item");

        assertDoesNotThrow(() -> collectible.setHumidityThreshold(50.0f));
    }

    @Test
    void setTemperatureThresholdUnderPossible() {
        Collectible collectible = new Collectible("Item");

        assertThrows(IllegalArgumentException.class, () -> collectible.setTemperatureThreshold(-300.0f));
    }

    @Test
    void setTemperatureThresholdPossible() {
        Collectible collectible = new Collectible("Item");

        assertDoesNotThrow(() -> collectible.setTemperatureThreshold(-200.0f));
    }
}