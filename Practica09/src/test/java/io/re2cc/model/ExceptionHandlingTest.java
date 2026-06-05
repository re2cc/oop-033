package io.re2cc.model;

import io.re2cc.controller.*;
import io.re2cc.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlingTest {
    private Showcase showcase;
    private ShowcaseManager manager;

    @BeforeEach
    void setUp() {
        showcase = new Showcase();
        manager = new ShowcaseManager();
    }

    @Test
    void showcaseCapacityExceeded() {
        try {
            for (int i = 1; i <= 5; i++) {
                manager.addCollectible(showcase, new PvcFigureCollectible("Item " + i));
            }
        } catch (CollectibleException e) {
            fail("Should not throw exceptions during normal setup additions: " + e.getMessage());
        }

        ShowcaseCapacityExceededException exception = assertThrows(ShowcaseCapacityExceededException.class, () -> {
            manager.addCollectible(showcase, new PvcFigureCollectible("Extra item"));
        });

        assertEquals(5, exception.getMaxCapacity());
        assertEquals(5, exception.getCurrentCount());
        assertTrue(exception.getMessage().contains("Showcase is full"));
    }

    @Test
    void duplicateCollectible() {
        PvcFigureCollectible pvcCollectible = new PvcFigureCollectible("PVC thing");
        try {
            manager.addCollectible(showcase, pvcCollectible);
        } catch (CollectibleException e) {
            fail("Should not throw exceptions for unique items.");
        }

        DuplicateCollectibleException exception = assertThrows(DuplicateCollectibleException.class, () -> {
            manager.addCollectible(showcase, new PvcFigureCollectible("PVC thing"));
        });

        assertEquals("PVC thing", exception.getDuplicateName());
        assertTrue(exception.getMessage().contains("Duplicate name found"));
    }

    @Test
    void preservationThresholdViolated() {
        WoodCollectible wrongMaterialCollectible = new WoodCollectible("Wood thing");
        try {
            manager.addCollectible(showcase, wrongMaterialCollectible);
        } catch (CollectibleException e) {
            fail("Failed to add initial collectible: " + e.getMessage());
        }

        PreservationThresholdViolatedException exception = assertThrows(PreservationThresholdViolatedException.class, () -> {
            float temp = 22.5f;
            float humidity = 45.0f;
            if (humidity > wrongMaterialCollectible.getHumidityThreshold()) {
                throw new PreservationThresholdViolatedException(
                    "Preservation warning: Humidity threshold exceeded.",
                    wrongMaterialCollectible.getName(), "Humidity", wrongMaterialCollectible.getHumidityThreshold(), humidity
                );
            }
        });

        assertEquals("Wood thing", exception.getCollectibleName());
        assertEquals("Humidity", exception.getParameterName());
        assertEquals(10.0f, exception.getThresholdLimit());
        assertEquals(45.0f, exception.getActualValue());
        assertNotNull(exception.getTimestamp());
    }

    @Test
    void sensorAutoCloseable() {
        try (EnvironmentalSensor sensor = new EnvironmentalSensor("SensorTest")) {
            assertEquals(22.5f, sensor.readTemperature());
            assertEquals(45.0f, sensor.readHumidity());
        } catch (Exception e) {
            fail("Sensor should not throw exceptions on close or read.");
        }
    }

    @Test
    void logFileCreation() {
        File logFile = new File("collectible_errors.log");
        assertTrue(logFile.exists(), "The log file collectible_errors.log must exist.");
    }
}
