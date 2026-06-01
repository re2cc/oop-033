package io.re2cc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalCollectibleTest {

    private static class TestPhysicalCollectible extends PhysicalCollectible {
        public TestPhysicalCollectible(String name, float uva, float uvb, float temp, float hum) {
            super(name, uva, uvb, temp, hum);
        }

        @Override public float getBaseDepreciationRate() { return 0.05f; }
        @Override public float getRiskMultiplier() { return 1.0f; }
        @Override public boolean needsSpecialCare() { return false; }
        @Override public String getPreservationInstructions() { return ""; }
        @Override public double estimateValue(double basePrice) { return basePrice; }
        @Override public double estimateValue(double basePrice, int yearsElapsed) { return basePrice; }
        @Override public double estimateValue(double basePrice, int yearsElapsed, boolean isRare) { return basePrice; }
    }

    @Test
    void setHumidityThresholdOver100() {
        PhysicalCollectible collectible = new TestPhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(101.0f));
    }

    @Test
    void setHumidityThresholdUnder100() {
        PhysicalCollectible collectible = new TestPhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> collectible.setHumidityThreshold(-1.0f));
    }

    @Test
    void setHumidityThresholdValid() {
        PhysicalCollectible collectible = new TestPhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertDoesNotThrow(() -> collectible.setHumidityThreshold(50.0f));
    }

    @Test
    void setTemperatureThresholdUnderPossible() {
        PhysicalCollectible collectible = new TestPhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> collectible.setTemperatureThreshold(-300.0f));
    }

    @Test
    void setTemperatureThresholdPossible() {
        PhysicalCollectible collectible = new TestPhysicalCollectible("Item", 0.5f, 0.5f, 0.5f, 0.5f);

        assertDoesNotThrow(() -> collectible.setTemperatureThreshold(-200.0f));
    }
}