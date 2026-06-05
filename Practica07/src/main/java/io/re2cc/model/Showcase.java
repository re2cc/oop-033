package io.re2cc.model;

import java.util.ArrayList;
import java.util.Optional;

public class Showcase {
    private ArrayList<PhysicalCollectible> storedCollectibles = new ArrayList<>();
    protected int capacity = 5; // Default capacity, other showcase types may have a different ones

    public ArrayList<PhysicalCollectible> getStoredCollectibles() {
        return storedCollectibles;
    }

    public int getCapacity() {
        return capacity;
    }

    public Showcase() {}

    public void addCollectible(PhysicalCollectible physicalCollectible) {
        if (storedCollectibles.size() < capacity) {
            storedCollectibles.add(physicalCollectible);
        } else {
            IO.println("Error: There is no more space in the showcase");
        }
    }

    public Optional<PhysicalCollectible> searchCollectible(String name) {
        for (PhysicalCollectible physicalCollectible : storedCollectibles) {
            if (physicalCollectible.getName().equals(name)) {
                return Optional.of(physicalCollectible);
            }
        }
        return Optional.empty();
    }

    public void showCollectibles() {
        for (Collectible physicalCollectible : storedCollectibles) {
            IO.println(physicalCollectible);
        }
    }

    public void showDisplayDetails() {
        for (PhysicalCollectible item : storedCollectibles) {
            if (item instanceof Displayable displayable) {
                IO.println("Collectible '" + item.getName() + "' Display: " + displayable.getDisplayType() +
                        " (Enclosed Cabinet required: " + displayable.requiresEnclosedCabinet() + ")");
            } else {
                IO.println("Collectible '" + item.getName() + "' cannot be displayed.");
            }
        }
    }

    public double calculateTotalValue(double basePrice) {
        double total = 0.0;
        for (PhysicalCollectible item : storedCollectibles) {
            if (item instanceof Valuable valuable) {
                total += valuable.estimateValue(basePrice);
            }
        }
        return total;
    }
}
