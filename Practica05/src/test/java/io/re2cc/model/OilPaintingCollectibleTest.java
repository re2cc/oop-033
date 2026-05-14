package io.re2cc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OilPaintingCollectibleTest {

    @Test
    void setUvbThresholdOverLimit() {
        OilPaintingCollectible collectible = new OilPaintingCollectible("Starry Night");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvbThreshold(51.0f));
    }

    @Test
    void setUvbThresholdUnderLimit() {
        OilPaintingCollectible collectible = new OilPaintingCollectible("Starry Night");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvbThreshold(-1.0f));
    }

    @Test
    void setUvaThresholdOverLimit() {
        OilPaintingCollectible collectible = new OilPaintingCollectible("Starry Night");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvaThreshold(51.0f));
    }

    @Test
    void setUvaThresholdUnderLimit() {
        OilPaintingCollectible collectible = new OilPaintingCollectible("Starry Night");
        assertThrows(IllegalArgumentException.class, () -> collectible.setUvaThreshold(-1.0f));
    }
}