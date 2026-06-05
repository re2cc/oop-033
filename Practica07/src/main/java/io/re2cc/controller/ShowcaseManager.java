package io.re2cc.controller;

import io.re2cc.model.*;
import io.re2cc.exception.*;
import java.util.logging.*;
import java.io.IOException;

public class ShowcaseManager {
    private Logger logger = Logger.getLogger(ShowcaseManager.class.getName());

    public ShowcaseManager() {
        try {
            FileHandler fileHandler = new FileHandler("collectible_errors.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            System.err.println("Could not initialize log file: " + e.getMessage());
        }
    }

    public void addCollectible(Showcase showcase, PhysicalCollectible item)
            throws ShowcaseCapacityExceededException, DuplicateCollectibleException {
        if (showcase.getStoredCollectibles().size() >= showcase.getCapacity()) {
            ShowcaseCapacityExceededException ex = new ShowcaseCapacityExceededException(
                    "Cannot add collectible: Showcase is full.", showcase.getCapacity(),
                    showcase.getStoredCollectibles().size());
            logger.log(Level.WARNING, "Capacity exceeded", ex);
            throw ex;
        }

        if (showcase.searchCollectible(item.getName()).isPresent()) {
            DuplicateCollectibleException ex = new DuplicateCollectibleException(
                    "Cannot add collectible: Duplicate name found.", item.getName());
            logger.log(Level.WARNING, "Duplicate collectible name", ex);
            throw ex;
        }

        showcase.addCollectible(item);
    }

    public void monitorEnvironment(Showcase showcase, String sensorId) {
        try (EnvironmentalSensor sensor = new EnvironmentalSensor(sensorId)) {
            float temp = sensor.readTemperature();
            float humidity = sensor.readHumidity();

            for (PhysicalCollectible item : showcase.getStoredCollectibles()) {
                if (item.hasExceedTemperatureThreshold(temp)) {
                    PreservationThresholdViolatedException ex = new PreservationThresholdViolatedException(
                            "Preservation warning: Temperature threshold exceeded for collectible.",
                            item.getName(), "Temperature", item.getTemperatureThreshold(), temp);
                    logger.log(Level.SEVERE, "Preservation violation detected", ex);
                    throw ex;
                }
                if (humidity > item.getHumidityThreshold()) {
                    PreservationThresholdViolatedException ex = new PreservationThresholdViolatedException(
                            "Preservation warning: Humidity threshold exceeded for collectible.",
                            item.getName(), "Humidity", item.getHumidityThreshold(), humidity);
                    logger.log(Level.SEVERE, "Preservation violation detected", ex);
                    throw ex;
                }
            }
        } catch (PreservationThresholdViolatedException e) {
            IO.println("Handled rule violation: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O Error while reading environmental sensor metrics", e);
            IO.println("Handled sensor I/O error: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred during environmental monitoring", e);
            IO.println("Handled unexpected error: " + e.getMessage());
        }
    }
}
