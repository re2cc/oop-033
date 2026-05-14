package io.re2cc.model;

import io.re2cc.model.Collectible;
import io.re2cc.model.Showcase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShowcaseTest {
    private Showcase showcase;

    @BeforeEach
    void setUp() {
        showcase = new Showcase();
    }

    @Test
    void addCollectible() {
        for (int i = 1; i <= 5; i++) {
            showcase.addCollectible(new WoodCollectible("Item " + i));
        }

        String extraItemName = "Item 6";
        PvcFigureCollectible extraItem = new PvcFigureCollectible(extraItemName);
        showcase.addCollectible(extraItem);

        Optional<PhysicalCollectible> result = showcase.searchCollectible(extraItemName);

        assertFalse(result.isPresent(), "The showcase should not allow adding items beyond its capacity of 5.");
    }
}